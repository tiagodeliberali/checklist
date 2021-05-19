package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerTests {
    @Test
    void empty_answer_scores_max() {
        Answer answer = Answer.create(new TopicId(UUID.randomUUID()));

        assertThat(answer.getScore()).isEqualTo(Grade.MAX);
    }

    @Test
    void add_missing_requirement_reduces_grade() throws TopicRequirementAlreadyExistsException {
        Answer answer = Answer.create(new TopicId(UUID.randomUUID()));

        answer.addMissingRequirement(Requirement.create(Grade.from(0.25), "Missing something"));

        assertThat(answer.getScore()).isEqualTo(Grade.from(0.75));
    }
}
