package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceGradeHeader {
    private String repo;
    private double grade;
    private List<ServiceThemeHeader> themesHeader;

    public ServiceGradeHeader() {
        this.themesHeader = new ArrayList<>();
    }
}
