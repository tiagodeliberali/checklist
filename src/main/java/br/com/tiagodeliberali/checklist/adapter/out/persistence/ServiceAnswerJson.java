package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceAnswerJson {
    private String topicId;
    private List<String> missedRequirements;

    public ServiceAnswerJson() {
        missedRequirements = new ArrayList<>();
    }
}
