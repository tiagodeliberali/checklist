package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TopicTests {
    @Test
    void create_topic_with_requirements() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 10);
        topic.add(Requirement.create(Grade.from(0.25), new RequirementName("description one")));
        topic.add(Requirement.create(Grade.from(0.25), new RequirementName("another description")));
        topic.add(Requirement.create(Grade.from(0.25), new RequirementName("a third description value")));
        topic.add(Requirement.create(Grade.from(0.25), new RequirementName("last issue to be named")));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
        assertThat(topic.count()).isEqualTo(4);
    }

    @Test
    void add_requirement_to_topic() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 5);

        topic.add(Grade.from(0.25), new RequirementName("description one"));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.25));
        assertThat(topic.count()).isEqualTo(1);
    }

    @Test
    void max_loss_is_one() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 10);
        topic.add(Requirement.create(Grade.from(0.75), new RequirementName("description one")));
        topic.add(Requirement.create(Grade.from(0.75), new RequirementName("another description")));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
    }

    @Test
    void remove_requirement_from_topic() throws EntityAlreadyExistException, EntityNotFoundException {
        Topic topic = Topic.create(new TopicName("testability"), 10);
        topic.add(Requirement.create(Grade.from(0.5), new RequirementName("description one")));
        topic.add(Requirement.create(Grade.from(0.4), new RequirementName("another description")));

        topic.remove(EntityId.from(new RequirementName("description one")));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.4));
        assertThat(topic.count()).isEqualTo(1);
    }

    @Test
    void calculate_grade_from_answer() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 10);
        topic.add(Requirement.create(Grade.from(0.5), new RequirementName("description one")));
        topic.add(Requirement.create(Grade.from(0.4), new RequirementName("another description")));
        topic.add(Requirement.create(Grade.from(0.3), new RequirementName("third description")));

        ServiceInfo service = ServiceInfo.create("test");
        service.addRequirement(
                new TopicName("testability"), EntityId.from(new RequirementName("third description")));
        service.addRequirement(
                new TopicName("testability"), EntityId.from(new RequirementName("another description")));

        Grade grade = topic.calculate(service);

        assertThat(grade).isEqualTo(Grade.from(0.3));
    }

    @Test
    void get_requirements_not_used() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 10);
        topic.add(Requirement.create(Grade.from(0.5), new RequirementName("description one")));
        topic.add(Requirement.create(Grade.from(0.4), new RequirementName("another description")));
        topic.add(Requirement.create(Grade.from(0.3), new RequirementName("third description")));

        Answer answer = Answer.create(new TopicName("testability"));
        answer.addMissingRequirement(EntityId.from(new RequirementName("third description")));
        answer.addMissingRequirement(EntityId.from(new RequirementName("another description")));

        Set<Requirement> unusedRequirements = topic.getUnusedRequirements(answer);

        assertThat(unusedRequirements.size()).isOne();

        Optional<Requirement> unused = unusedRequirements.stream()
                .filter(x -> x.getName().equals(new RequirementName("description one")))
                .findFirst();

        assertThat(unused).isPresent();
    }
}
