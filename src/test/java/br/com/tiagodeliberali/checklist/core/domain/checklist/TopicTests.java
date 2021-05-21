package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TopicTests {
    @Test
    void create_topic_with_requirements() {
        Topic topic = Topic.create(
                new TopicName("testability"),
                10,
                Arrays.asList(
                    Requirement.create(Grade.from(0.25), new RequirementName("description one")),
                    Requirement.create(Grade.from(0.25), new RequirementName("another description")),
                    Requirement.create(Grade.from(0.25), new RequirementName("a third description value")),
                    Requirement.create(Grade.from(0.25), new RequirementName("last issue to be named"))
                )
        );

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
        assertThat(topic.getRequirementsCount()).isEqualTo(4);
    }

    @Test
    void add_requirement_to_topic() throws RequirementAlreadyExistsException {
        Topic topic = Topic.create(new TopicName("testability"), 5);

        topic.addRequirement(Grade.from(0.25), new RequirementName("description one"));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.25));
        assertThat(topic.getRequirementsCount()).isEqualTo(1);
    }

    @Test
    void max_loss_is_one() {
        Topic topic = Topic.create(
                new TopicName("testability"),
                10,
                Arrays.asList(
                        Requirement.create(Grade.from(0.75), new RequirementName("description one")),
                        Requirement.create(Grade.from(0.75), new RequirementName("another description"))
                )
        );

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
    }

    @Test
    void remove_requirement_from_topic() throws RequirementNotFoundException {
        Topic topic = Topic.create(
                new TopicName("testability"),
                10,
                Arrays.asList(
                        Requirement.create(Grade.from(0.5), new RequirementName("description one")),
                        Requirement.create(Grade.from(0.4), new RequirementName("another description"))
                )
        );

        topic.removeRequirement(new RequirementName("description one"));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.4));
        assertThat(topic.getRequirementsCount()).isEqualTo(1);
    }

    @Test
    void calculate_grade_from_answer() {
        Topic topic = Topic.create(
                new TopicName("testability"),
                10,
                Arrays.asList(
                        Requirement.create(Grade.from(0.5), new RequirementName("description one")),
                        Requirement.create(Grade.from(0.4), new RequirementName("another description")),
                        Requirement.create(Grade.from(0.3), new RequirementName("third description"))
                )
        );

        Answer answer = Answer.create(new TopicName("testability"));
        answer.addMissingRequirement(new RequirementName("third description"));
        answer.addMissingRequirement(new RequirementName("another description"));

        Grade grade = topic.getGrade(answer);

        assertThat(grade).isEqualTo(Grade.from(0.3));
    }

    @Test
    void get_requirements_not_used() {
        Topic topic = Topic.create(
                new TopicName("testability"),
                10,
                Arrays.asList(
                        Requirement.create(Grade.from(0.5), new RequirementName("description one")),
                        Requirement.create(Grade.from(0.4), new RequirementName("another description")),
                        Requirement.create(Grade.from(0.3), new RequirementName("third description"))
                )
        );

        Answer answer = Answer.create(new TopicName("testability"));
        answer.addMissingRequirement(new RequirementName("third description"));
        answer.addMissingRequirement(new RequirementName("another description"));

        Set<Requirement> unusedRequirements = topic.getUnusedRequirements(answer);

        assertThat(unusedRequirements.size()).isOne();

        Optional<Requirement> unused = unusedRequirements.stream()
                .filter(x -> x.name().equals(new RequirementName("description one")))
                .findFirst();

        assertThat(unused).isPresent();
    }
}
