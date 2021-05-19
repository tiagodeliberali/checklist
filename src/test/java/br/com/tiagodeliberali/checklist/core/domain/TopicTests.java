package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class TopicTests {
    @Test
    void create_topic_with_requirements() {
        Topic topic = Topic.create(
                "testability",
                Theme.create(6, "stability"),
                Arrays.asList(
                    TopicRequirement.create(Grade.from(0.25), "description one"),
                    TopicRequirement.create(Grade.from(0.25), "another description"),
                    TopicRequirement.create(Grade.from(0.25), "a third description value"),
                    TopicRequirement.create(Grade.from(0.25), "last issue to be named")
                )
        );

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
        assertThat(topic.getRequirementsCount()).isEqualTo(4);
    }

    @Test
    void add_requirement_to_topic() throws TopicRequirementAlreadyExistsException {
        Topic topic = Topic.create("testability", Theme.create(6, "stability"));

        topic.addRequirement(Grade.from(0.25), "description one");

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.25));
        assertThat(topic.getRequirementsCount()).isEqualTo(1);
    }

    @Test
    void max_loss_is_one() {
        Topic topic = Topic.create(
                "testability",
                Theme.create(6, "stability"),
                Arrays.asList(
                        TopicRequirement.create(Grade.from(0.75), "description one"),
                        TopicRequirement.create(Grade.from(0.75), "another description")
                )
        );

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
    }

    @Test
    void remove_requirement_from_topic() throws TopicRequirementNotFoundException {
        Topic topic = Topic.create(
                "testability",
                Theme.create(6, "stability"),
                Arrays.asList(
                        TopicRequirement.create(Grade.from(0.5), "description one"),
                        TopicRequirement.create(Grade.from(0.4), "another description")
                )
        );

        topic.removeRequirement(Grade.from(0.4), "another description");

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.5));
        assertThat(topic.getRequirementsCount()).isEqualTo(1);
    }
}
