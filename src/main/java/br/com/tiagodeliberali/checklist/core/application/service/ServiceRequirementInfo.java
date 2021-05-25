package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceRequirementInfo {
    private String id;
    private String name;
    private double grade;
}
