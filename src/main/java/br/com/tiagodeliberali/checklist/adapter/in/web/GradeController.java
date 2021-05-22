package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.in.CalculateGradesUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeController {
    private final CalculateGradesUseCase calculateGradesUseCase;

    @Autowired
    public GradeController(CalculateGradesUseCase calculateGradesUseCase) {
        this.calculateGradesUseCase = calculateGradesUseCase;
    }

    @GetMapping(path = "/grade/{checklist}/{service}")
    public ServiceGrade getServiceGrade(
            @PathVariable("checklist") String checklist,
            @PathVariable("service") String service) throws FailedToLoadException {
        return calculateGradesUseCase.calculate(checklist, service);
    }
}
