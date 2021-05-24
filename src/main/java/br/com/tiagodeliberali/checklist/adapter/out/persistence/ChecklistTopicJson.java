package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistTopicJson {
    private String id;
    private String name;
    private int weight;
    private List<ChecklistRequirementJson> requirements;

    public ChecklistTopicJson() {
        requirements = new ArrayList<>();
    }
}
