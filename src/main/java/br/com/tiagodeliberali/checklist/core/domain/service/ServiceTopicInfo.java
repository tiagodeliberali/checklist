package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Requirement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ServiceTopicInfo implements Serializable {
    private Grade grade;
    private Set<Requirement> unusedRequirements;

    public ServiceTopicInfo() {
        unusedRequirements = new HashSet<>();
    }
}
