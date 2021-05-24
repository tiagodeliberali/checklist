package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;

public interface ManipulateChecklistUseCase {
    void create(String name) throws EntityAlreadyExistException, FailedToSaveException;
    void addTheme(String checklistName, String name, int weight) throws FailedToLoadException, EntityAlreadyExistException, FailedToSaveException;
    void addTopic(String checklistName, String themeId, String name, int weight) throws FailedToLoadException, EntityAlreadyExistException, FailedToSaveException;
    void addRequirement(String checklistName, String themeId, String topicId,  String name, double grade) throws FailedToLoadException, EntityAlreadyExistException, FailedToSaveException;
    Checklist loadChecklist(String name) throws FailedToLoadException;
}
