package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@AllArgsConstructor
public class Checklist {
    private final String name;
    private final List<Topic> topics;

    public static Checklist create(String description) {
        return new Checklist(description, new ArrayList<>());
    }

    public void add(Topic topic) {
        topics.add(topic);
    }

    public int countTopics() {
        return topics.size();
    }

    public long countThemes() {
        return distinctThemes().count();
    }

    public Grade calculate(ServiceInfo service) {
        Map<Theme, List<Grade>> grades = new HashMap<>();
        distinctThemes().forEach(theme -> grades.put(theme, new ArrayList<>()));

        topics.stream().forEach(topic -> {
            Grade result = service.getAnswer(topic.getName())
                    .map(answer -> topic.getGrade(answer))
                    .orElse(Grade.MIN);

            grades.computeIfPresent(topic.getTheme(), (key, val) -> {
                val.add(result);
                return val;
            });
        });

        double total = 0;
        int count = 0;
        for (Theme theme: grades.keySet()) {
            double sum = grades.get(theme).stream().mapToDouble(x -> x.grade().doubleValue()).sum();
            double avg = sum / grades.get(theme).size();
            total += avg * theme.weight();
            count += theme.weight();
        }

        return Grade.MIN.plus(total / count);
    }

    private Stream<Theme> distinctThemes() {
        return topics.stream()
                .map(Topic::getTheme)
                .distinct();
    }

    public Iterator<Topic> getTopicIterator() {
        return topics.iterator();
    }
}
