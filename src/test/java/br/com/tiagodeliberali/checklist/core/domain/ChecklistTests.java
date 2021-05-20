package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChecklistTests {
    @Test
    void create_checklist() {
        Checklist checklist = Checklist.create("tribe services");

        checklist.add(Topic.create(new TopicName("testability"), 10, Theme.create(5, "stability")));

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

        checklist.getTopicIterator().forEachRemaining(x -> service.addTopic(x.getName()));

        Grade grade = checklist.calculate(service);

        assertThat(grade).isEqualTo(Grade.MAX);
    }

    @Test
    void calculate_partially_answered_service_info() throws TopicRequirementAlreadyExistsException {
        // arrange
        Checklist checklist = Checklist.create("tribe services");

        Topic topic1 = Topic.create(new TopicName("testability"), 10, Theme.create(5, "stability"));
        topic1.addRequirement(Grade.from(0.25), new RequirementName("should have manual tests description"));
        topic1.addRequirement(Grade.from(0.5), new RequirementName("should have unit tests"));
        checklist.add(topic1);

        Topic topic2 = Topic.create(new TopicName("database migration"), 6, Theme.create(5, "stability"));
        topic2.addRequirement(Grade.from(0.4), new RequirementName("should have migration"));
        checklist.add(topic2);

        Topic topic3 = Topic.create(new TopicName("kibana log"), 10, Theme.create(3, "monitoring"));
        topic3.addRequirement(Grade.from(0.7), new RequirementName("should not break one log by line"));
        topic3.addRequirement(Grade.from(0.5), new RequirementName("should have relevant id data"));
        topic3.addRequirement(Grade.from(0.3), new RequirementName("should have trace id"));
        checklist.add(topic3);

        ServiceInfo service = ServiceInfo.create("crm-pwa");

        // add topics 1 and 3
        checklist.getTopicIterator().forEachRemaining(topic -> {
                    if (!topic.getName().id().equals("database migration"))
                        service.addTopic(topic.getName());
        });


        // act
        Grade grade = checklist.calculate(service);

        // assert
        assertThat(grade).isEqualTo(Grade.MAX);
    }

    private Checklist buildChecklist() {
        Checklist checklist = Checklist.create("tribe services");

        checklist.add(Topic.create(new TopicName("testability"), 10, Theme.create(5, "stability")));
        checklist.add(Topic.create(new TopicName("health check"), 5, Theme.create(5, "stability")));
        checklist.add(Topic.create(new TopicName("prometheus"), 3, Theme.create(4, "monitoring")));
        return checklist;
    }
}
