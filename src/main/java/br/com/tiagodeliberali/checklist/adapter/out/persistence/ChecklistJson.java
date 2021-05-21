package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistJson {
    private String name;
    private List<ChecklistThemeJson> themes;

    public ChecklistJson() {
        themes = new ArrayList<>();
    }
}
