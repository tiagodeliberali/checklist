package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;

import java.util.Map;
import java.util.Set;

public interface IndexServiceUseCase {
    Map<String, Set<String>> getServiceByRequirements() throws FailedToLoadException;
    Set<String> getServiceByRequirementsAnd(Set<String> requirements) throws FailedToLoadException;
    Set<String> getServiceByRequirementsOr(Set<String> requirements) throws FailedToLoadException;
}