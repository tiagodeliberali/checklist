package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GradeTests {
    @Test
    void add_value_to_grade_max_to_MAX() {
        Grade grade = Grade.MIN;

        Grade result = grade.plus(3);

        assertThat(result).isEqualTo(Grade.MAX);
    }

    @Test
    void add_value_to_grade_inside_range() {
        Grade grade = Grade.from(0.2);

        Grade result = grade.plus(0.3);

        assertThat(result).isEqualTo(Grade.from(0.5));
    }

    @Test
    void reduce_value_from_grade_min_to_MIN() {
        Grade grade = Grade.MAX;

        Grade result = grade.minus(3);

        assertThat(result).isEqualTo(Grade.MIN);
    }

    @Test
    void reduce_value_inside_range() {
        Grade grade = Grade.from(0.8);

        Grade result = grade.minus(0.2);

        assertThat(result).isEqualTo(Grade.from(0.6));
    }

    @Test
    void grade_has_4_digits_rounded_up() {
        assertThat(Grade.from(0.2345678)).isEqualTo(Grade.from(0.2346));
    }
}
