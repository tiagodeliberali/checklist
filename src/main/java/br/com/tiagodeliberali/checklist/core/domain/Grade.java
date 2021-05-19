package br.com.tiagodeliberali.checklist.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Grade(BigDecimal grade) {
    public static Grade MAX = Grade.from(1);
    public static Grade MIN = Grade.from(0);

    public static Grade from(double value) {
        return new Grade(new BigDecimal(value));
    }

    public Grade {
        if (grade.doubleValue() > 1 || grade.doubleValue() < 0) {
            throw new IllegalArgumentException("Grade should be between 0 and 1");
        }

        grade = grade.setScale(4, RoundingMode.HALF_UP);
    }

    public Grade minus(double value) {
        return new Grade(grade.add(BigDecimal.valueOf(value).negate()).max(BigDecimal.ZERO));
    }

    public Grade plus(double value) {
        return new Grade(grade.add(BigDecimal.valueOf(value)).min(BigDecimal.ONE));
    }
}
