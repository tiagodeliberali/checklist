package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.SetRequirementAssert;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ThemeTests {
    @Test
    void add_requirement() throws EntityAlreadyExistException {
        Theme theme = createTheme();

        assertThat(theme.count()).isEqualTo(2);
        assertThat(theme.get(EntityId.from(new TopicName("testability"))).count()).isEqualTo(3);
    }

    @Test
    void calculate_grade() throws EntityAlreadyExistException {
        Theme theme = createTheme();
        ServiceInfo service = createService();

        Grade grade = theme.calculate(service);

        assertThat(grade).isEqualTo(Grade.from(0.4857));
    }

    @Test
    void return_unused_requirements() throws EntityAlreadyExistException {
        Theme theme = createTheme();
        ServiceInfo service = createService();

        Set<Requirement> unusedRequirements = theme.getUnusedRequirements(service);

        assertThat(unusedRequirements.size()).isEqualTo(2);
        SetRequirementAssert.assertThat(unusedRequirements).contains(new RequirementName("not in ci/cd"));
        SetRequirementAssert.assertThat(unusedRequirements).contains(new RequirementName("not validating sqs"));
    }

    private ServiceInfo createService() {
        ServiceInfo service = ServiceInfo.create("us-tasks");
        service.addRequirement(EntityId.from(new TopicName("testability")), EntityId.from(new RequirementName("missing manual tests doc")));
        service.addRequirement(EntityId.from(new TopicName("testability")), EntityId.from(new RequirementName("no unit tests")));
        service.addRequirement(EntityId.from(new TopicName("health check")), EntityId.from(new RequirementName("not validating database")));
        return service;
    }

    private Theme createTheme() throws EntityAlreadyExistException {
        Theme theme = Theme.create(new ThemeName("scalability"), 10);

        Topic topic1 = Topic.create(new TopicName("testability"), 5);
        topic1.add(Requirement.create(Grade.from(0.2), new RequirementName("missing manual tests doc")));
        topic1.add(Requirement.create(Grade.from(0.4), new RequirementName("no unit tests")));
        topic1.add(Requirement.create(Grade.from(0.8), new RequirementName("not in ci/cd")));

        theme.add(topic1);

        Topic topic2 = Topic.create(new TopicName("health check"), 2);
        topic2.add(Requirement.create(Grade.from(0.3), new RequirementName("not validating database")));
        topic2.add(Requirement.create(Grade.from(0.6), new RequirementName("not validating sqs")));

        theme.add(topic2);

        return theme;
    }
}
