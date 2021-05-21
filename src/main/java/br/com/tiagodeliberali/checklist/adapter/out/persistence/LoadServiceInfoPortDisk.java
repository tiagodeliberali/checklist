package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotFound;
import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotParsed;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LoadServiceInfoPortDisk implements LoadServiceInfoPort {
    @Override
    public ServiceInfo load(String jsonPath) throws JsonFileNotFound, JsonFileNotParsed {
        try {
            Path path = Paths.get(jsonPath);
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
            throw new JsonFileNotFound(jsonPath, ex);
        }
        catch (Exception ex) {
            throw new JsonFileNotParsed(jsonPath, ex);
        }
    }
}
