package com.example.application.views.about;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

        private Image lolaImg = new Image("images/lola-dawg.png", "lola dawg");

    public AboutView() {
        setSpacing(false);

        lolaImg.setWidth("250px");
        add(lolaImg);

        add(new H2("Lola's here to guide you through your visit."));

        VirtualList<String> aboutLola = new VirtualList<>();
        aboutLola.setItems("She's a dog.","She's an astronaut.","She's cuter than she is smart.");
        add(aboutLola);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
