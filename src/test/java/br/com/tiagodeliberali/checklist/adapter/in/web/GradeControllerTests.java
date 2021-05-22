package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.in.CalculateGradesUseCase;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceThemeInfo;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceTopicInfo;
import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Requirement;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GradeController.class)
class GradeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalculateGradesUseCase calculateGradesUseCase;

    @Test
    void getServiceGrade() throws Exception {
        // arrange
        ServiceGrade grade = new ServiceGrade();
        grade.setGrade(0.75);

        ServiceThemeInfo themeInfo = new ServiceThemeInfo();
        grade.getThemesInfo().put("theme_name", themeInfo);
        themeInfo.setGrade(0.5);

        ServiceTopicInfo topicInfo = new ServiceTopicInfo();
        topicInfo.setGrade(0.25);
        topicInfo.getUnusedRequirements().put("requirement", 0.1);
        themeInfo.getTopicsInfo().put("topic_name", topicInfo);

        when(calculateGradesUseCase.calculate("checklist", "service")).thenReturn(grade);

        // act
        MvcResult result = mockMvc.perform(get("/grade/checklist/service")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        // assert
        assertThat(jsonResult)
                .isEqualTo("{\"grade\":0.75,\"themesInfo\":{" +
                        "\"theme_name\":{\"grade\":0.5,\"topicsInfo\":{" +
                        "\"topic_name\":{\"grade\":0.25,\"unusedRequirements\":{" +
                        "\"requirement\":0.1}}}}}}");
    }
}
