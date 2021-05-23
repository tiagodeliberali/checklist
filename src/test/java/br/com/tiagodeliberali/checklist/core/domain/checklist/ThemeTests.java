package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.SetRequirementAssert;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ThemeTests {
    @Test
    void add_requirement() throws RequirementAlreadyExistsException, TopicNotFoundException, ItemAlreadyExistException {
        Theme theme = Theme.create(new ThemeName("scalability"), 10);

        theme.add(Topic.create(new TopicName("testability"), 5));
        theme.addRequirement(
                new TopicName("testability"),
                Requirement.create(Grade.from(0.25), new RequirementName("missing manual tests doc")));

        assertThat(theme.count()).isOne();
        assertThat(theme.getByName(new TopicName("testability")).getRequirementsCount()).isOne();
    }

    @Test
    void add_requirement_when_topic_not_exists_throws_exception() {
        Theme theme = Theme.create(new ThemeName("scalability"), 10);

        Assertions.assertThrows(TopicNotFoundException.class, () -> theme.addRequirement(
                new TopicName("testability"),
                Requirement.create(Grade.from(0.25), new RequirementName("missing manual tests doc")))
        );
    }

    @Test
    void calculate_grade() throws ItemAlreadyExistException, RequirementAlreadyExistsException {
        Theme theme = createTheme();

        ServiceInfo service = ServiceInfo.create("us-tasks");
        service.addRequirement(new TopicName("testability"), new RequirementName("missing manual tests doc"));
        service.addRequirement(new TopicName("testability"), new RequirementName("no unit tests"));
        service.addRequirement(new TopicName("health check"), new RequirementName("not validating database"));

        Grade grade = theme.calculate(service);

        assertThat(grade).isEqualTo(Grade.from(0.4857));
    }

    @Test
    void return_unused_requirements() throws ItemAlreadyExistException, RequirementAlreadyExistsException {
        Theme theme = createTheme();

        ServiceInfo service = ServiceInfo.create("us-tasks");
        service.addRequirement(new TopicName("testability"), new RequirementName("missing manual tests doc"));
        service.addRequirement(new TopicName("testability"), new RequirementName("no unit tests"));
        service.addRequirement(new TopicName("health check"), new RequirementName("not validating database"));

        Set<Requirement> unusedRequirements = theme.getUnusedRequirements(service);

        assertThat(unusedRequirements.size()).isEqualTo(2);
        SetRequirementAssert.assertThat(unusedRequirements).contains(new RequirementName("not in ci/cd"));
        SetRequirementAssert.assertThat(unusedRequirements).contains(new RequirementName("not validating sqs"));
    }

    private Theme createTheme() throws ItemAlreadyExistException, RequirementAlreadyExistsException {
        Theme theme = Theme.create(new ThemeName("scalability"), 10);

        Topic topic1 = Topic.create(new TopicName("testability"), 5);
        topic1.addRequirement(Requirement.create(Grade.from(0.2), new RequirementName("missing manual tests doc")));
        topic1.addRequirement(Requirement.create(Grade.from(0.4), new RequirementName("no unit tests")));
        topic1.addRequirement(Requirement.create(Grade.from(0.8), new RequirementName("not in ci/cd")));

        theme.add(topic1);

        Topic topic2 = Topic.create(new TopicName("health check"), 2);
        topic2.addRequirement(Requirement.create(Grade.from(0.3), new RequirementName("not validating database")));
        topic2.addRequirement(Requirement.create(Grade.from(0.6), new RequirementName("not validating sqs")));

        theme.add(topic2);

        return theme;
    }
}
