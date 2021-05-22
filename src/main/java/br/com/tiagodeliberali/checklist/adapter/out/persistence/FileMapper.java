package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementAlreadyExistsException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Theme;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Topic;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMapper {
    private static Logger logger = LoggerFactory.getLogger(LoadChecklistDisk.class);

    public static Checklist from(ChecklistJson json) {
        Checklist checklist = Checklist.create(json.getName());

        json.getThemes().forEach(themeJson -> {
            Theme theme = Theme.create(new ThemeName(themeJson.getName()), themeJson.getWeight());
            checklist.add(theme);

            themeJson.getTopics().forEach(topicJson -> {
                Topic topic = Topic.create(new TopicName(topicJson.getName()), topicJson.getWeight());
                theme.add(topic);

                topicJson.getRequirements().forEach(requirementJson -> {
                    try {
                        topic.addRequirement(
                                Grade.from(requirementJson.getGrade()),
                                new RequirementName(requirementJson.getName()));
                    } catch (RequirementAlreadyExistsException e) {
                        logger.warn("[LoadChecklistDisk] " + e.getMessage());
                    }
                });
            });
        });

        return checklist;
    }

    public static ServiceInfo from(ServiceJson json) {
        ServiceInfo serviceInfo = ServiceInfo.create(json.getRepo());

        json.getAnswers().forEach(answerJson -> answerJson
                .getMissedRequirements()
                .forEach(requirement -> serviceInfo
                        .addRequirement(new TopicName(answerJson.getTopicName()), new RequirementName(requirement))));

        return serviceInfo;
    }
}
