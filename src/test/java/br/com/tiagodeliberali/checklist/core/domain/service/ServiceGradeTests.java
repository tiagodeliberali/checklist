package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.SetRequirementAssert;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementAlreadyExistsException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Theme;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Topic;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceGradeTests {
    @Test
    void build_grade() throws RequirementAlreadyExistsException {
        // arrange
        Checklist checklist = Checklist.create("tribe services");

        Theme theme1 = Theme.create(new ThemeName("scalability"), 1);
        Topic topic1 = Topic.create(new TopicName("topic1"), 1);
        topic1.addRequirement(Grade.from(0.5), new RequirementName("req1"));
        topic1.addRequirement(Grade.from(0.5), new RequirementName("req2"));
        theme1.add(topic1);

        Topic topic2 = Topic.create(new TopicName("topic2"), 1);
        topic2.addRequirement(Grade.from(0.5), new RequirementName("req3"));
        theme1.add(topic2);
        checklist.add(theme1);

        Theme theme2 = Theme.create(new ThemeName("monitoring"), 1);
        Topic topic3 = Topic.create(new TopicName("topic3"), 1);
        topic3.addRequirement(Grade.from(0.5), new RequirementName("req4"));
        topic3.addRequirement(Grade.from(0.5), new RequirementName("req5"));
        topic3.addRequirement(Grade.from(0.5), new RequirementName("req6"));
        theme2.add(topic3);
        checklist.add(theme2);

        ServiceInfo service = ServiceInfo.create("crm-pwa");
        service.addRequirement(new TopicName("topic1"), new RequirementName("req1"));
        service.addRequirement(new TopicName("topic3"), new RequirementName("req5"));
        service.addRequirement(new TopicName("topic3"), new RequirementName("req6"));

        // act
        ServiceGrade serviceGrade = ServiceGrade.build(checklist, service);

        // assert
        assertThat(serviceGrade.getGrade()).isEqualTo(Grade.from(0.1250));

        ServiceThemeInfo themeInfo1 = serviceGrade.getThemesInfo().get(new ThemeName("scalability"));
        assertThat(themeInfo1.getGrade()).isEqualTo(Grade.from(0.250));

        ServiceTopicInfo topicInfo1 = themeInfo1.getTopicsInfo().get(new TopicName("topic1"));
        assertThat(topicInfo1.getGrade()).isEqualTo(Grade.from(0.5));
        SetRequirementAssert.assertThat(topicInfo1.getUnusedRequirements()).contains(new RequirementName("req2"));

        ServiceTopicInfo topicInfo2 = themeInfo1.getTopicsInfo().get(new TopicName("topic2"));
        assertThat(topicInfo2.getGrade()).isEqualTo(Grade.MIN);
        SetRequirementAssert.assertThat(topicInfo2.getUnusedRequirements()).contains(new RequirementName("req3"));

        ServiceThemeInfo themeInfo2 = serviceGrade.getThemesInfo().get(new ThemeName("monitoring"));
        assertThat(themeInfo2.getGrade()).isEqualTo(Grade.MIN);

        ServiceTopicInfo topicInfo3 = themeInfo2.getTopicsInfo().get(new TopicName("topic3"));
        assertThat(topicInfo3.getGrade()).isEqualTo(Grade.MIN);
        SetRequirementAssert.assertThat(topicInfo3.getUnusedRequirements()).contains(new RequirementName("req4"));
    }
}
