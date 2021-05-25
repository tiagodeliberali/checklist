package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.adapter.out.persistence.FileMapper;
import br.com.tiagodeliberali.checklist.adapter.out.persistence.ServiceJson;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceInfoService;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ServiceController {
    private final ServiceInfoService serviceInfoService;

    @Autowired
    public ServiceController(ServiceInfoService serviceInfoService) {
        this.serviceInfoService = serviceInfoService;
    }

    @GetMapping(path = "/service/{name}")
    public ServiceJson getService(@PathVariable("name") String name) throws FailedToLoadException {
        ServiceInfo service = serviceInfoService.loadServiceInfo(name);
        return FileMapper.from(service);
    }

    @PutMapping(path = "/service/{name}")
    public void create(@PathVariable("name") String name) throws EntityAlreadyExistException, FailedToSaveException {
        serviceInfoService.create(name);
    }

    @PutMapping(path = "/service/{name}/{topic}")
    public void addTopic(@PathVariable("name") String name,
                         @PathVariable("topic") String topic) throws FailedToLoadException, FailedToSaveException {
        serviceInfoService.addTopic(name, topic);
    }

    @PutMapping(path = "/service/{name}/{topic}/{requirement}")
    public void addRequirement(@PathVariable("name") String name,
                               @PathVariable("topic") String topic,
                               @PathVariable("requirement") String requirement) throws FailedToLoadException, FailedToSaveException {
        serviceInfoService.addRequirement(name, topic, requirement);
    }

    @DeleteMapping(path = "/service/{name}/{topic}/{requirement}")
    public void removeRequirement(@PathVariable("name") String name,
                               @PathVariable("topic") String topic,
                               @PathVariable("requirement") String requirement) throws FailedToLoadException, FailedToSaveException {
        serviceInfoService.removeRequirement(name, topic, requirement);
    }
}
