package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

public interface ManipulateServiceInfoUseCase {
    ServiceInfo loadServiceInfo(String name) throws FailedToLoadException;
    void create(String repo) throws EntityAlreadyExistException;
    void addTopic(String repo, String topic) throws FailedToLoadException;
    void addRequirement(String repo, String topic, String requirement) throws FailedToLoadException;
}
