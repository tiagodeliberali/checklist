package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceRequirementInfo;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceThemeInfo;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceTopicInfo;
import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Requirement;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Theme;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Topic;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceGradeTests {
    @Test
    void build_grade() throws EntityAlreadyExistException {
        // arrange
        Checklist checklist = Checklist.create("tribe services");

        Theme theme1 = Theme.create(new ThemeName("scalability"), 1);
        Topic topic1 = Topic.create(new TopicName("topic1"), 1);
        topic1.add(Requirement.create(new RequirementName("req1"), Grade.from(0.5)));
        topic1.add(Requirement.create(new RequirementName("req2"), Grade.from(0.5)));
        theme1.add(topic1);

        Topic topic2 = Topic.create(new TopicName("topic2"), 1);
        topic2.add(Requirement.create(new RequirementName("req3"), Grade.from(0.5)));
        theme1.add(topic2);
        checklist.add(theme1);

        Theme theme2 = Theme.create(new ThemeName("monitoring"), 1);
        Topic topic3 = Topic.create(new TopicName("topic3"), 1);
        topic3.add(Requirement.create(new RequirementName("req4"), Grade.from(0.5)));
        topic3.add(Requirement.create(new RequirementName("req5"), Grade.from(0.5)));
        topic3.add(Requirement.create(new RequirementName("req6"), Grade.from(0.5)));
        theme2.add(topic3);
        checklist.add(theme2);

        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addRequirement(EntityId.from(new TopicName("topic1")), EntityId.from(new RequirementName("req1")));
        service.addRequirement(EntityId.from(new TopicName("topic3")), EntityId.from(new RequirementName("req5")));
        service.addRequirement(EntityId.from(new TopicName("topic3")), EntityId.from(new RequirementName("req6")));

        // act
        ServiceGrade serviceGrade = ServiceGrade.build(checklist, service);

        // assert
        assertThat(serviceGrade.getGrade()).isEqualTo(0.1250);

        Optional<ServiceThemeInfo> themeInfo1 = serviceGrade.getTheme("scalability");
        assertThat(themeInfo1).isPresent();
        assertThat(themeInfo1.get().getGrade()).isEqualTo(0.250);

        Optional<ServiceTopicInfo> topicInfo1 = getTopic(themeInfo1.get().getTopicsInfo(), "topic1");
        assertThat(topicInfo1).isPresent();
        assertThat(topicInfo1.get().getGrade()).isEqualTo(0.5);
        assertThatContainsRequirementName(topicInfo1.get().getMissedRequirements(), "req1");
        assertThatContainsRequirementName(topicInfo1.get().getUnusedRequirements(), "req2");

        Optional<ServiceTopicInfo> topicInfo2 = getTopic(themeInfo1.get().getTopicsInfo(), "topic2");
        assertThat(topicInfo2).isPresent();
        assertThat(topicInfo2.get().getGrade()).isEqualTo(0);
        assertThat(topicInfo2.get().getMissedRequirements()).isEmpty();
        assertThatContainsRequirementName(topicInfo2.get().getUnusedRequirements(), "req3");

        Optional<ServiceThemeInfo> themeInfo2 = serviceGrade.getTheme("monitoring");
        assertThat(themeInfo2).isPresent();
        assertThat(themeInfo2.get().getGrade()).isEqualTo(0);

        Optional<ServiceTopicInfo> topicInfo3 = getTopic(themeInfo2.get().getTopicsInfo(), "topic3");
        assertThat(topicInfo3).isPresent();
        assertThat(topicInfo3.get().getGrade()).isEqualTo(0);
        assertThatContainsRequirementName(topicInfo3.get().getMissedRequirements(), "req5");
        assertThatContainsRequirementName(topicInfo3.get().getMissedRequirements(), "req6");
        assertThatContainsRequirementName(topicInfo3.get().getUnusedRequirements(), "req4");
    }

    private Optional<ServiceTopicInfo> getTopic(List<ServiceTopicInfo> topicsInfo, String name) {
        return topicsInfo.stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    private void assertThatContainsRequirementName(List<ServiceRequirementInfo> list, String name) {
        assertThat(list.stream().filter(x -> x.getName().equals(name)).findFirst()).isPresent();
    }
}
