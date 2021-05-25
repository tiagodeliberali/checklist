package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.adapter.out.persistence.ChecklistJson;
import br.com.tiagodeliberali.checklist.adapter.out.persistence.FileMapper;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.application.service.ChecklistService;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ChecklistController {
    private final ChecklistService checklistService;

    @Autowired
    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @GetMapping(path = "/checklist/{name}")
    public ChecklistJson getChecklist(@PathVariable("name") String name) throws FailedToLoadException {
        Checklist checklist = checklistService.loadChecklist(name);
        return FileMapper.from(checklist);
    }

    @PutMapping(path = "/checklist/{name}")
    public void createChecklist(@PathVariable("name") String name)
            throws EntityAlreadyExistException, FailedToSaveException {
        checklistService.create(name);
    }

    @PutMapping(path = "/checklist/{name}/{theme}/{weight}")
    public void addTheme(@PathVariable("name") String name,
                         @PathVariable("theme") String theme,
                         @PathVariable("weight") int weight)
            throws EntityAlreadyExistException, FailedToLoadException, FailedToSaveException {
        checklistService.addTheme(name, theme, weight);
    }

    @PutMapping(path = "/checklist/{name}/{theme}/{topic}/{weight}")
    public void addTopic(@PathVariable("name") String name,
                         @PathVariable("theme") String theme,
                         @PathVariable("topic") String topic,
                         @PathVariable("weight") int weight)
            throws EntityAlreadyExistException, FailedToLoadException, FailedToSaveException {
        checklistService.addTopic(name, theme, topic, weight);
    }

    @PutMapping(path = "/checklist/{name}/{theme}/{topic}/{requirement}/{grade}")
    public void addRequirement(@PathVariable("name") String name,
                               @PathVariable("theme") String theme,
                               @PathVariable("topic") String topic,
                               @PathVariable("requirement") String requirement,
                               @PathVariable("grade") double grade)
            throws EntityAlreadyExistException, FailedToLoadException, FailedToSaveException {
        checklistService.addRequirement(name, theme, topic, requirement, grade);
    }
}
