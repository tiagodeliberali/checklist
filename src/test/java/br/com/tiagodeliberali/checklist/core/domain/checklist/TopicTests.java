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
        Topic topic = createTopic();

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
        assertThat(topic.count()).isEqualTo(3);
    }

    @Test
    void add_requirement_to_topic() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 5);

        topic.add(Requirement.create(new RequirementName("description one"), Grade.from(0.25)));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.25));
        assertThat(topic.count()).isEqualTo(1);
    }

    @Test
    void remove_requirement_from_topic() throws EntityAlreadyExistException, EntityNotFoundException {
        Topic topic = createTopic();

        topic.remove(EntityId.from(new RequirementName("description one")));

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.7));
        assertThat(topic.count()).isEqualTo(2);
    }

    @Test
    void calculate_grade_from_answer() throws EntityAlreadyExistException {
        Topic topic = createTopic();

        ServiceInfo service = ServiceInfo.create("test");
        service.addRequirement(
                EntityId.from(new TopicName("testability")), EntityId.from(new RequirementName("third description")));
        service.addRequirement(
                EntityId.from(new TopicName("testability")), EntityId.from(new RequirementName("another description")));

        Grade grade = topic.calculate(service);

        assertThat(grade).isEqualTo(Grade.from(0.3));
    }

    @Test
    void get_requirements_not_used() throws EntityAlreadyExistException {
        Topic topic = createTopic();

        Answer answer = Answer.create();
        answer.addMissingRequirement(EntityId.from(new RequirementName("third description")));
        answer.addMissingRequirement(EntityId.from(new RequirementName("another description")));

        Set<Requirement> unusedRequirements = topic.getUnusedRequirements(answer);

        assertThat(unusedRequirements.size()).isOne();

        Optional<Requirement> unused = unusedRequirements.stream()
                .filter(x -> x.getName().equals(new RequirementName("description one")))
                .findFirst();

        assertThat(unused).isPresent();
    }

    private Topic createTopic() throws EntityAlreadyExistException {
        Topic topic = Topic.create(new TopicName("testability"), 10);
        topic.add(Requirement.create(new RequirementName("description one"), Grade.from(0.5)));
        topic.add(Requirement.create(new RequirementName("another description"), Grade.from(0.4)));
        topic.add(Requirement.create(new RequirementName("third description"), Grade.from(0.3)));

        return topic;
    }
}
