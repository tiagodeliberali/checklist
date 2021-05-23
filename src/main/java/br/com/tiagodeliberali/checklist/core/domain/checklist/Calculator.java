package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

import java.util.Collection;

public class Calculator {
    public static <ITEM extends WeightCalculableEntity> Grade weightAverage(Collection<ITEM> nodes, ServiceInfo service) {
        double total = 0;
        int count = 0;

        for (WeightCalculableEntity entity: nodes) {
            total += entity.calculate(service).grade().doubleValue() * entity.getWeight();
            count += entity.getWeight();
        }

        return Grade.from(total / count);
    }
}
