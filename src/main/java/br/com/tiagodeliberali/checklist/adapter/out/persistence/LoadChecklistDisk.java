package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LoadChecklistDisk implements LoadChecklistPort {
    private final String folderPath;

    @Autowired
    public LoadChecklistDisk(@Value("${folder.path}") String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public Checklist load(String name) throws FailedToLoadException {
        Path path = Paths.get(folderPath, String.format("%s.json", name));
        try {
            String jsonStr = Files.readString(path);

            ObjectMapper mapper = new ObjectMapper();
            ChecklistJson json = mapper.readValue(jsonStr, ChecklistJson.class);

            return FileMapper.from(json);
        }
        catch (Exception ex) {
            throw new FailedToLoadException(path.toString(), ex);
        }
    }
}
