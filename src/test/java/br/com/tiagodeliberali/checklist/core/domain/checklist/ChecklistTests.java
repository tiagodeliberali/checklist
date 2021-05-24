package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChecklistTests {
    @Test
    void create_checklist_with_multiple_topics_returns_correct_amounts() throws EntityAlreadyExistException {
        Checklist checklist = buildChecklist();

        assertThat(checklist.count()).isEqualTo(2);
        assertThat(checklist.countTopics()).isEqualTo(3);
    }

    @Test
    void calculate_grade_should_require_service_with_answers() throws EntityAlreadyExistException {
        Checklist checklist = buildChecklist();

        Grade grade = checklist.calculate(ServiceInfo.create("crm-pwa"));

        assertThat(grade).isEqualTo(Grade.MIN);
    }

    @Test
    void calculate_grade_without_missing_requirements_gets_max_grade() throws EntityAlreadyExistException {
        Checklist checklist = buildChecklist();

        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addTopic(EntityId.from(new TopicName("testability")));
        service.addTopic(EntityId.from(new TopicName("database migration")));
        service.addTopic(EntityId.from(new TopicName("kibana log")));

        Grade grade = checklist.calculate(service);

        assertThat(grade).isEqualTo(Grade.MAX);
    }

    @Test
    void calculate_partially_answered_service_info() throws EntityAlreadyExistException {
        // arrange
        Checklist checklist = buildChecklist();

        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addRequirement(EntityId.from(new TopicName("testability")),
                EntityId.from(new RequirementName("should have manual tests description")));
        service.addRequirement(EntityId.from(new TopicName("kibana log")),
                EntityId.from(new RequirementName("should have relevant name data")));
        service.addRequirement(EntityId.from(new TopicName("kibana log")),
                EntityId.from(new RequirementName("should have trace name")));

        // act
        Grade grade = checklist.calculate(service);

        // assert
        assertThat(grade).isEqualTo(Grade.from(0.36796875));
    }

    private Checklist buildChecklist() throws EntityAlreadyExistException {
        Checklist checklist = Checklist.create("tribe services");

        Theme theme1 = Theme.create(new ThemeName("scalability"), 5);
        Topic topic1 = Topic.create(new TopicName("testability"), 10);
        topic1.add(Requirement.create(new RequirementName("should have manual tests description"), Grade.from(0.25)));
        topic1.add(Requirement.create(new RequirementName("should have unit tests"), Grade.from(0.5)));
        theme1.add(topic1);

        Topic topic2 = Topic.create(new TopicName("database migration"), 6);
        topic2.add(Requirement.create(new RequirementName("should have migration"), Grade.from(0.4)));
        theme1.add(topic2);
        checklist.add(theme1);

        Theme theme2 = Theme.create(new ThemeName("monitoring"), 3);
        Topic topic3 = Topic.create(new TopicName("kibana log"), 10);
        topic3.add(Requirement.create(new RequirementName("should not break one log by line"), Grade.from(0.7)));
        topic3.add(Requirement.create(new RequirementName("should have relevant name data"), Grade.from(0.5)));
        topic3.add(Requirement.create(new RequirementName("should have trace name"), Grade.from(0.3)));
        theme2.add(topic3);
        checklist.add(theme2);

        return checklist;
    }
}
