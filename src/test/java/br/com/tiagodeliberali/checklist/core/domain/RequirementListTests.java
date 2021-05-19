package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class RequirementListTests {
    @Test
    void should_throw_exception_if_try_to_remove_requirement_that_not_exists() {
        RequirementList list = RequirementList.from(Arrays.asList(
                TopicRequirement.create(Grade.from(0.5), "description one"),
                TopicRequirement.create(Grade.from(0.4), "another description")
        ));

        Assertions.assertThrows(TopicRequirementNotFoundException.class, () ->
                list.remove(TopicRequirement.create(Grade.from(0.5), "new requirement")));
    }

    @Test
    void should_throw_exception_if_try_to_add_existing_requirement() {
        RequirementList list = RequirementList.from(Arrays.asList(
                        TopicRequirement.create(Grade.from(0.5), "description one"),
                        TopicRequirement.create(Grade.from(0.4), "another description")
                )
        );

        Assertions.assertThrows(TopicRequirementAlreadyExistsException.class, () ->
                list.add(TopicRequirement.create(Grade.from(0.4), "another description")));
    }
}
