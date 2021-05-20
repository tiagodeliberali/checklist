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
        Checklist checklist = buildChecklist();

        assertThat(checklist.countTopics()).isEqualTo(3);
        assertThat(checklist.countThemes()).isEqualTo(2);
    }

    @Test
    void calculate_grade_should_require_service_with_answers() {
        Checklist checklist = buildChecklist();

        Grade grade = checklist.calculate(ServiceInfo.create("crm-pwa"));

        assertThat(grade).isEqualTo(Grade.MIN);
    }

    @Test
    void calculate_grade_without_missing_requirements_gets_max_grade() {
        Checklist checklist = buildChecklist();
        ServiceInfo service = ServiceInfo.create("crm-pwa");

        checklist.getTopicIterator().forEachRemaining(x -> service.addTopic(x.getId()));

        Grade grade = checklist.calculate(service);

        assertThat(grade).isEqualTo(Grade.MAX);
    }

    private Checklist buildChecklist() {
        Checklist checklist = Checklist.create("tribe services");

        checklist.add(Topic.create("testability", 10, Theme.create(5, "stability")));
        checklist.add(Topic.create("health check", 5, Theme.create(5, "stability")));
        checklist.add(Topic.create("prometheus", 3, Theme.create(4, "monitoring")));
        return checklist;
    }
}
