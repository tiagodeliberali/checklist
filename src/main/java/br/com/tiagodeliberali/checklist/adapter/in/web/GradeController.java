package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.in.CalculateGradesUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotFound;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeController {
    private final CalculateGradesUseCase calculateGradesUseCase;

    @Autowired
    public GradeController(CalculateGradesUseCase calculateGradesUseCase) {
        this.calculateGradesUseCase = calculateGradesUseCase;
    }

    @GetMapping(path = "/grade/{service}")
    public ServiceGrade getServiceGrade(String service) throws JsonFileNotFound {
        return calculateGradesUseCase.calculate(service);
    }
}
