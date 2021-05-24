package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SaveChecklistPortDisk implements SaveChecklistPort {
    private final String folderPath;

    @Autowired
    public SaveChecklistPortDisk(@Value("${folder.path}") String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public void save(Checklist checklist) throws FailedToSaveException {
        Path path = Paths.get(folderPath, String.format("%s.json", checklist.getName()));

        ChecklistJson json = FileMapper.from(checklist);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(path.toFile(), json);
        } catch (IOException e) {
            throw new FailedToSaveException(path.toString(), e);
        }
    }
}
