package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.adapter.out.persistence.LoadChecklistPortDisk;
import br.com.tiagodeliberali.checklist.core.application.port.in.ManipulateChecklistUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Requirement;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Theme;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Topic;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChecklistService implements ManipulateChecklistUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ChecklistService.class);

    private final LoadChecklistPort loadChecklistPort;
    private final SaveChecklistPort saveChecklistPort;

    public ChecklistService(LoadChecklistPort loadChecklistPort, SaveChecklistPort saveChecklistPort) {
        this.loadChecklistPort = loadChecklistPort;
        this.saveChecklistPort = saveChecklistPort;
    }

    @Override
    public void create(String name) throws EntityAlreadyExistException, FailedToSaveException {
        try {
            Checklist checklist = loadChecklistPort.load(name);

            if (checklist != null) {
                throw new EntityAlreadyExistException(new EntityId(name));
            }
        } catch (FailedToLoadException e) {
            logger.warn(e.getMessage());
        }

        Checklist checklist = Checklist.create(name);
        saveChecklistPort.save(checklist);
    }

    @Override
    public void addTheme(String checklistName, String name, int weight)
            throws FailedToLoadException, EntityAlreadyExistException, FailedToSaveException {
        Checklist checklist = loadChecklistPort.load(checklistName);
        checklist.add(Theme.create(new ThemeName(name), weight));
        saveChecklistPort.save(checklist);
    }

    @Override
    public void addTopic(String checklistName, String themeId, String name, int weight)
            throws FailedToLoadException, EntityAlreadyExistException, FailedToSaveException {
        Checklist checklist = loadChecklistPort.load(checklistName);
        checklist.get(new EntityId(themeId)).add(Topic.create(new TopicName(name), weight));
        saveChecklistPort.save(checklist);
    }

    @Override
    public void addRequirement(String checklistName, String themeId, String topicId, String name, double grade)
            throws FailedToLoadException, EntityAlreadyExistException, FailedToSaveException {
        Checklist checklist = loadChecklistPort.load(checklistName);
        checklist.get(new EntityId(themeId))
                .get(new EntityId(topicId))
                .add(Requirement.create(Grade.from(grade), new RequirementName(name)));
        saveChecklistPort.save(checklist);
    }

    @Override
    public Checklist loadChecklist(String name) throws FailedToLoadException {
        return loadChecklistPort.load(name);
    }
}
