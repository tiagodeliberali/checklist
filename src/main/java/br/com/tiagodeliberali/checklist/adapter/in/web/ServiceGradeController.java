package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.CalculateGradeService;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGradeHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ServiceGradeController {
    private final CalculateGradeService gradeService;

    @Autowired
    public ServiceGradeController(CalculateGradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping(path = "/grade/{checklist}/{service}")
    public ServiceGrade getGrade(@PathVariable("checklist") String checklist,
                                 @PathVariable("service") String service) throws FailedToLoadException {
        return gradeService.calculate(checklist, service);
    }

    @GetMapping(path = "/grade/{checklist}/all")
    public List<ServiceGradeHeader> getAllGrades(@PathVariable("checklist") String checklist)
            throws FailedToLoadException {
        return gradeService.calculateAll(checklist);
    }
}
