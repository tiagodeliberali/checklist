package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotFound;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LoadServiceInfoPortDisk implements LoadServiceInfoPort {
    private final String folderPath;

    @Autowired
    public LoadServiceInfoPortDisk(@Value("${folder.path}") String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public ServiceInfo load(String name) throws JsonFileNotFound {
        Path path = Paths.get(folderPath, String.format("%s.json", name));
        try {
            String jsonStr = Files.readString(path);

            ObjectMapper mapper = new ObjectMapper();
            ServiceJson json = mapper.readValue(jsonStr, ServiceJson.class);

            ServiceInfo serviceInfo = ServiceInfo.create(json.getRepo());

            json.getAnswers().forEach(answerJson -> answerJson
                    .getMissedRequirements()
                    .forEach(r -> serviceInfo
                            .addRequirement(new TopicName(answerJson.getTopicName()), new RequirementName(r))));

            return serviceInfo;
        }
        catch (IOException ex) {
            throw new JsonFileNotFound(path.toString(), ex);
        }
        catch (Exception ex) {
            throw new JsonFileNotFound(path.toString(), ex);
        }
    }
}
