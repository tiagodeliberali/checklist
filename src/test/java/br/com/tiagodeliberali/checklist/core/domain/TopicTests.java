package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class TopicTests {
    @Test
    void create_topic_with_requirements() {
        Topic topic = Topic.create(
                "testability",
                10,
                Theme.create(6, "stability"),
                Arrays.asList(
                    Requirement.create(Grade.from(0.25), "description one"),
                    Requirement.create(Grade.from(0.25), "another description"),
                    Requirement.create(Grade.from(0.25), "a third description value"),
                    Requirement.create(Grade.from(0.25), "last issue to be named")
                )
        );

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
        assertThat(topic.getRequirementsCount()).isEqualTo(4);
    }

    @Test
    void add_requirement_to_topic() throws TopicRequirementAlreadyExistsException {
        Topic topic = Topic.create("testability", 5, Theme.create(6, "stability"));

        topic.addRequirement(Grade.from(0.25), "description one");

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.25));
        assertThat(topic.getRequirementsCount()).isEqualTo(1);
    }

    @Test
    void max_loss_is_one() {
        Topic topic = Topic.create(
                "testability",
                10,
                Theme.create(6, "stability"),
                Arrays.asList(
                        Requirement.create(Grade.from(0.75), "description one"),
                        Requirement.create(Grade.from(0.75), "another description")
                )
        );

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.MAX);
    }

    @Test
    void remove_requirement_from_topic() throws TopicRequirementNotFoundException {
        Requirement requirement = Requirement.create(Grade.from(0.5), "description one");
        Topic topic = Topic.create(
                "testability",
                10,
                Theme.create(6, "stability"),
                Arrays.asList(
                        requirement,
                        Requirement.create(Grade.from(0.4), "another description")
                )
        );

        topic.removeRequirement(requirement.getId());

        assertThat(topic.getMaxLoss()).isEqualTo(Grade.from(0.4));
        assertThat(topic.getRequirementsCount()).isEqualTo(1);
    }
}
