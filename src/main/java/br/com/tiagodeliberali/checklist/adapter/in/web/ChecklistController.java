package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.ChecklistService;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ChecklistController {
    private final ChecklistService checklistService;

    @Autowired
    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @GetMapping(path = "/checklist/{name}")
    public ChecklistResource getChecklist(@PathVariable("name") String name) throws FailedToLoadException {
        Checklist checklist = checklistService.loadChecklist(name);
        return ResourceMapper.from(checklist);
    }

    @PostMapping(path = "/checklist/{name}")
    public void saveChecklist(@PathVariable("name") String name,
                              @Valid @RequestBody ChecklistResource checklist) {
        checklistService.save(ResourceMapper.from(checklist));
    }
}
