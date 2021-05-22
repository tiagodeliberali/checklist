package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.CalculateGradeService;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {
    @Autowired
    private CalculateGradeService calculateGradeService;

    @Autowired
    public MainView() {
        HorizontalLayout serviceGradeDetails = new HorizontalLayout();
        TextField checklistName = new TextField();
        Button loadServiceButton = new Button("Load");
        H2 serviceGradeValue = new H2();

        loadServiceButton.addClickShortcut(Key.ENTER);
        loadServiceButton.addClickListener(click ->
                loadServiceGradeInfo(serviceGradeDetails, checklistName, serviceGradeValue));

        add(new HorizontalLayout(
                        checklistName,
                        loadServiceButton
                ),
                serviceGradeValue,
                serviceGradeDetails);
    }

    private void loadServiceGradeInfo(HorizontalLayout serviceGradeDetails, TextField checklistName, H2 serviceGradeValue) {
        serviceGradeDetails.removeAll();
        try {
            ServiceGrade serviceGrade =
                    calculateGradeService.calculate("checklist", checklistName.getValue());

            serviceGradeValue.setText(String.format("Service grade: %.2f", serviceGrade.getGrade()));

            serviceGrade.getThemesInfo().forEach((themeName, themeInfo) -> {
                VerticalLayout themeLayout = new VerticalLayout();
                // themeLayout.setWidth(400, Unit.POINTS);
                themeLayout.add(new H3(String.format("%s: %.2f", themeName, themeInfo.getGrade())));

                themeInfo.getTopicsInfo().forEach((topicName, topicInfo) -> {
                    themeLayout.add(new H4(String.format("%s: %.2f", topicName, topicInfo.getGrade())));

                    VerticalLayout missedRequirements = new VerticalLayout();
                    VerticalLayout unusedRequirements = new VerticalLayout();

                    themeLayout.add(new HorizontalLayout(
                            missedRequirements,
                            unusedRequirements
                    ));

                    topicInfo.getMissedRequirements().forEach((req, grade) ->
                            missedRequirements.add(new Label(String.format("%s (%s)", req, grade))));

                    topicInfo.getUnusedRequirements().forEach((req, grade) ->
                            unusedRequirements.add(new Label(String.format("%s (%s)", req, grade))));
                });

                serviceGradeDetails.add(themeLayout);
            });
        } catch (FailedToLoadException e) {
            Notification.show("Error:" + e.getMessage());
        }
    }
}
