package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementAlreadyExistsException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Theme;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Topic;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LoadChecklistDisk implements LoadChecklistPort {
    Logger logger = LoggerFactory.getLogger(LoadChecklistDisk.class);
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

            Checklist checklist = Checklist.create(json.getName());

            json.getThemes().forEach(themeJson -> {
                Theme theme = Theme.create(new ThemeName(themeJson.getName()), themeJson.getWeight());
                checklist.add(theme);

                themeJson.getTopics().forEach(topicJson -> {
                    Topic topic = Topic.create(new TopicName(topicJson.getName()), topicJson.getWeight());
                    theme.add(topic);

                    topicJson.getRequirements().forEach(requirementJson -> {
                        try {
                            topic.addRequirement(
                                    Grade.from(requirementJson.getGrade()),
                                    new RequirementName(requirementJson.getName()));
                        } catch (RequirementAlreadyExistsException e) {
                            logger.warn("[LoadChecklistDisk] " + e.getMessage());
                        }
                    });
                });
            });

            return checklist;
        }
        catch (Exception ex) {
            throw new FailedToLoadException(path.toString(), ex);
        }
    }
}
