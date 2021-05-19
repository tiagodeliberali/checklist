package br.com.tiagodeliberali.checklist.core.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceTests {
    @Test
    void add_new_topic_missing_requirements_create_if_not_exists() {
        ServiceInfo serviceInfo = ServiceInfo.create("crm-pwa");

        serviceInfo.addTopic(new TopicId(UUID.randomUUID()));

        assertThat(serviceInfo.count()).isEqualTo(1);
    }
}
