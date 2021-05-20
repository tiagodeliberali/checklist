package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChecklistTests {
    @Test
    void create_checklist_with_multiple_topics_returns_correct_amounts() throws RequirementAlreadyExistsException {
        Checklist checklist = buildChecklist();

        assertThat(checklist.countThemes()).isEqualTo(2);
        assertThat(checklist.countTopics()).isEqualTo(3);
    }

    @Test
    void calculate_grade_should_require_service_with_answers() throws RequirementAlreadyExistsException {
        Checklist checklist = buildChecklist();

        Grade grade = checklist.calculate(ServiceInfo.create("crm-pwa"));

        assertThat(grade).isEqualTo(Grade.MIN);
    }

    @Test
    void calculate_grade_without_missing_requirements_gets_max_grade() throws RequirementAlreadyExistsException {
        Checklist checklist = buildChecklist();
        
        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addTopic(new TopicName("testability"));
        service.addTopic(new TopicName("database migration"));
        service.addTopic(new TopicName("kibana log"));

        Grade grade = checklist.calculate(service);

        assertThat(grade).isEqualTo(Grade.MAX);
    }

    @Test
    void calculate_partially_answered_service_info() throws RequirementAlreadyExistsException {
        // arrange
        Checklist checklist = buildChecklist();

        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addRequirement(new TopicName("testability"), new RequirementName("should have manual tests description"));
        service.addRequirement(new TopicName("kibana log"), new RequirementName("should have relevant id data"));
        service.addRequirement(new TopicName("kibana log"), new RequirementName("should have trace id"));

        // act
        Grade grade = checklist.calculate(service);

        // assert
        assertThat(grade).isEqualTo(Grade.MAX);
    }

    private Checklist buildChecklist() throws RequirementAlreadyExistsException {
        Checklist checklist = Checklist.create("tribe services");

        Theme theme1 = Theme.create(new ThemeName("scalability"), 5);
        Topic topic1 = Topic.create(new TopicName("testability"), 10);
        topic1.addRequirement(Grade.from(0.25), new RequirementName("should have manual tests description"));
        topic1.addRequirement(Grade.from(0.5), new RequirementName("should have unit tests"));
        theme1.add(topic1);

        Topic topic2 = Topic.create(new TopicName("database migration"), 6);
        topic2.addRequirement(Grade.from(0.4), new RequirementName("should have migration"));
        theme1.add(topic2);
        checklist.add(theme1);

        Theme theme2 = Theme.create(new ThemeName("monitoring"), 5);
        Topic topic3 = Topic.create(new TopicName("kibana log"), 10);
        topic3.addRequirement(Grade.from(0.7), new RequirementName("should not break one log by line"));
        topic3.addRequirement(Grade.from(0.5), new RequirementName("should have relevant id data"));
        topic3.addRequirement(Grade.from(0.3), new RequirementName("should have trace id"));
        theme2.add(topic3);
        checklist.add(theme2);

        return checklist;
    }
}
