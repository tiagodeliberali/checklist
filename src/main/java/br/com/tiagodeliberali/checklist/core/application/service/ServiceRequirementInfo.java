package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequirementInfo {
    private String name;
    private double grade;
}
