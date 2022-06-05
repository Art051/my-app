package com.example.application.views.learningresources;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Learning Resources")
@Route(value = "", layout = MainLayout.class)
public class LearningResourcesView extends HorizontalLayout {

    private Button ahButton;
    private Paragraph paragraph;

    public LearningResourcesView() {

        paragraph = new Paragraph();
        paragraph.setText("This is a simple website to practice using Vaadin framework and to provide a bingo card generator for someone.");

        StringBuilder ahhText = new StringBuilder("Ahh");

        ahButton = new Button("Clicky click");
        ahButton.addClickListener(e -> {
            if(e.getClickCount() < 5){
                ahhText.append("h");
                Notification.show(ahhText.toString());
            }
            else{
                ahButton.setDisableOnClick(true);
                Notification.show("Now look what you've gone and done");
            }
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, ahButton);

        add(paragraph, ahButton);
    }

}
