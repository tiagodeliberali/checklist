package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChecklistTests {
    @Test
    void create_checklist() {
        Checklist checklist = Checklist.create("tribe services");

        checklist.add(Topic.create("testability", 10, Theme.create(5, "stability")));

        assertThat(checklist.countTopics()).isOne();
        assertThat(checklist.countThemes()).isOne();
    }

    @Test
    void create_checklist_with_multiple_topics_returns_correct_amounts() {
        Checklist checklist = Checklist.create("tribe services");

        checklist.add(Topic.create("testability", 10, Theme.create(5, "stability")));
        checklist.add(Topic.create("health check", 5, Theme.create(5, "stability")));
        checklist.add(Topic.create("prometheus", 3, Theme.create(4, "monitoring")));

        assertThat(checklist.countTopics()).isEqualTo(3);
        assertThat(checklist.countThemes()).isEqualTo(2);
    }
}
