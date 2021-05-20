package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Checklist {
    private final String name;
    private final List<Theme> themes;

    public static Checklist create(String description) {
        return new Checklist(description, new ArrayList<>());
    }

    public void add(Theme theme) {
        themes.add(theme);
    }

    public int countTopics() {
        return themes.stream().mapToInt(x -> x.getTopics().size()).sum();
    }

    public long countThemes() {
        return themes.size();
    }

    public Grade calculate(ServiceInfo service) {
        return Grade.MIN;
    }
}
