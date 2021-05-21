package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
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
        double total = 0;
        int count = 0;

        for (Theme theme: themes) {
            total += theme.calculate(service).grade().doubleValue() * theme.getWeight();
            count += theme.getWeight();
        }

        return Grade.from(total / count);
    }

    public Iterator<Theme> getThemeIterator() {
        return themes.iterator();
    }
}
