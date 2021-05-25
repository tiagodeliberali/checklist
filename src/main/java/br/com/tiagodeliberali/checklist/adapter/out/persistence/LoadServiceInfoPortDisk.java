package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoadServiceInfoPortDisk implements LoadServiceInfoPort {
    private final String folderPath;

    @Autowired
    public LoadServiceInfoPortDisk(@Value("${service.folder.path}") String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public ServiceInfo load(String name) throws FailedToLoadException {
        Path path = Paths.get(folderPath, String.format("%s.json", name));
        try {
            String jsonStr = Files.readString(path);

            ObjectMapper mapper = new ObjectMapper();
            ServiceJson json = mapper.readValue(jsonStr, ServiceJson.class);

            return FileMapper.from(json);
        } catch (Exception ex) {
            throw new FailedToLoadException(path.toString(), ex);
        }
    }

    @Override
    public List<String> loadAll() throws FailedToLoadException {
        try {
            return Files.list(Paths.get(folderPath))
                    .map(path -> path.getFileName().toString().replace(".json", ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FailedToLoadException(folderPath, e);
        }
    }
}
