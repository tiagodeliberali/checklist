package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceJson {
    private String repo;
    private List<ServiceAnswerJson> answers;

    public ServiceJson() {
        answers = new ArrayList<>();
    }
}
