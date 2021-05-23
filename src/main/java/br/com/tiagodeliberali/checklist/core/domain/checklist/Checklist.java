package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;

public class Checklist extends NodeInfo<Theme> {
    @Getter
    private final String name;

    private Checklist(String name) {
        this.name = name;
    }

    public static Checklist create(String name) {
        return new Checklist(name);
    }

    public int countTopics() {
        return nodes.values().stream().mapToInt(NodeInfo::count).sum();
    }

    public Grade calculate(ServiceInfo service) {
        return Calculator.weightAverage(nodes.values(), service);
    }
}
