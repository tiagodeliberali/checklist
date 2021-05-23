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

import static org.assertj.core.api.Assertions.assertThat;

class ServiceGradeTests {
    @Test
    void build_grade() throws EntityAlreadyExistException {
        // arrange
        Checklist checklist = Checklist.create("tribe services");

        Theme theme1 = Theme.create(new ThemeName("scalability"), 1);
        Topic topic1 = Topic.create(new TopicName("topic1"), 1);
        topic1.add(Requirement.create(Grade.from(0.5), new RequirementName("req1")));
        topic1.add(Requirement.create(Grade.from(0.5), new RequirementName("req2")));
        theme1.add(topic1);

        Topic topic2 = Topic.create(new TopicName("topic2"), 1);
        topic2.add(Requirement.create(Grade.from(0.5), new RequirementName("req3")));
        theme1.add(topic2);
        checklist.add(theme1);

        Theme theme2 = Theme.create(new ThemeName("monitoring"), 1);
        Topic topic3 = Topic.create(new TopicName("topic3"), 1);
        topic3.add(Requirement.create(Grade.from(0.5), new RequirementName("req4")));
        topic3.add(Requirement.create(Grade.from(0.5), new RequirementName("req5")));
        topic3.add(Requirement.create(Grade.from(0.5), new RequirementName("req6")));
        theme2.add(topic3);
        checklist.add(theme2);

        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addRequirement(new TopicName("topic1"), EntityId.from(new RequirementName("req1")));
        service.addRequirement(new TopicName("topic3"), EntityId.from(new RequirementName("req5")));
        service.addRequirement(new TopicName("topic3"), EntityId.from(new RequirementName("req6")));

        // act
        ServiceGrade serviceGrade = ServiceGrade.build(checklist, service);

        // assert
        assertThat(serviceGrade.getGrade()).isEqualTo(0.1250);

        ServiceThemeInfo themeInfo1 = serviceGrade.getTheme("scalability").get();
        assertThat(themeInfo1.getGrade()).isEqualTo(0.250);

        ServiceTopicInfo topicInfo1 = themeInfo1.getTopic("topic1").get();
        assertThat(topicInfo1.getGrade()).isEqualTo(0.5);
        assertThatContainsRequirementName(topicInfo1.getMissedRequirements(), "req1");
        assertThatContainsRequirementName(topicInfo1.getUnusedRequirements(), "req2");

        ServiceTopicInfo topicInfo2 = themeInfo1.getTopic("topic2").get();
        assertThat(topicInfo2.getGrade()).isEqualTo(0);
        assertThat(topicInfo2.getMissedRequirements()).isEmpty();
        assertThatContainsRequirementName(topicInfo2.getUnusedRequirements(), "req3");

        ServiceThemeInfo themeInfo2 = serviceGrade.getTheme("monitoring").get();
        assertThat(themeInfo2.getGrade()).isEqualTo(0);

        ServiceTopicInfo topicInfo3 = themeInfo2.getTopic("topic3").get();
        assertThat(topicInfo3.getGrade()).isEqualTo(0);
        assertThatContainsRequirementName(topicInfo3.getMissedRequirements(), "req5");
        assertThatContainsRequirementName(topicInfo3.getMissedRequirements(), "req6");
        assertThatContainsRequirementName(topicInfo3.getUnusedRequirements(), "req4");
    }

    private void assertThatContainsRequirementName(List<ServiceRequirementInfo> list, String name) {
        assertThat(list.stream().filter(x -> x.getName().equals(name)).findFirst()).isPresent();
    }
}
