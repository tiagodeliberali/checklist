package br.com.tiagodeliberali.checklist.adapter.out.persistence;

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
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FileMapper {
    private static final Logger logger = LoggerFactory.getLogger(LoadChecklistPortDisk.class);

    public static Checklist fromReliableSource(ChecklistJson json) {
        Checklist checklist = Checklist.create(json.getName());

        json.getThemes().forEach(themeJson -> {
            Theme theme = Theme.load(themeJson.getId(), themeJson.getName(), themeJson.getWeight());
            try {
                checklist.add(theme);
            } catch (EntityAlreadyExistException e) {
                logger.warn(e.getMessage());
            }

            themeJson.getTopics().forEach(topicJson -> {
                Topic topic = Topic.load(topicJson.getId(), topicJson.getName(), topicJson.getWeight());
                try {
                    theme.add(topic);
                } catch (EntityAlreadyExistException e) {
                    logger.warn(e.getMessage());
                }

                topicJson.getRequirements().forEach(requirementJson -> {
                    try {
                        topic.add(Requirement.load(
                                requirementJson.getId(), requirementJson.getName(), requirementJson.getGrade()));
                    } catch (EntityAlreadyExistException e) {
                        logger.warn("[LoadChecklistDisk] " + e.getMessage());
                    }
                });
            });
        });

        return checklist;
    }

    public static ServiceInfo from(ServiceJson json) {
        ServiceInfo serviceInfo = ServiceInfo.create(json.getRepo());

        json.getAnswers().forEach(answerJson -> {
            List<String> missedRequirements = answerJson.getMissedRequirements();

            if (missedRequirements.size() > 0) {
                missedRequirements.forEach(requirement -> serviceInfo
                        .addRequirement(
                                new EntityId(answerJson.getTopicId()),
                                new EntityId(requirement)));
            } else {
                serviceInfo.addTopic(new EntityId(answerJson.getTopicId()));
            }
        });

        return serviceInfo;
    }

    public static ChecklistJson from(Checklist checklist) {
        ChecklistJson json = new ChecklistJson();

        json.setName(checklist.getName());

        checklist.getIterator().forEachRemaining(theme -> {
            ChecklistThemeJson themeJson = new ChecklistThemeJson();
            themeJson.setId(theme.getId().id());
            themeJson.setName(theme.getName().name());
            themeJson.setWeight(theme.getWeight());
            json.getThemes().add(themeJson);

            theme.getIterator().forEachRemaining(topic -> {
                ChecklistTopicJson topicJson = new ChecklistTopicJson();
                topicJson.setId(topic.getId().id());
                topicJson.setName(topic.getName().name());
                topicJson.setWeight(topic.getWeight());
                themeJson.getTopics().add(topicJson);

                topic.getIterator().forEachRemaining(requirement -> {
                    ChecklistRequirementJson requirementJson = new ChecklistRequirementJson();
                    requirementJson.setId(requirement.getId().id());
                    requirementJson.setName(requirement.getName().name());
                    requirementJson.setGrade(requirement.getGrade().grade().doubleValue());
                    topicJson.getRequirements().add(requirementJson);
                });

            });

        });

        return json;
    }

    public static ServiceJson from(ServiceInfo serviceInfo) {
        ServiceJson json = new ServiceJson();
        json.setRepo(serviceInfo.getRepo());

        serviceInfo.getIterator().forEachRemaining(topicId -> {
            ServiceAnswerJson answerJson = new ServiceAnswerJson();
            answerJson.setTopicId(topicId.id());
            json.getAnswers().add(answerJson);

            serviceInfo.getAnswer(topicId).ifPresent(answer ->
                    answer.getIterator().forEachRemaining(requirementId ->
                            answerJson.getMissedRequirements().add(requirementId.id())));

        });

        return json;
    }
}
