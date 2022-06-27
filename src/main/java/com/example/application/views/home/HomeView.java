package com.example.application.views.home;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private final Paragraph paragraph;

    public HomeView() {

        paragraph = new Paragraph();
        paragraph.setText("This is a simple website to practice using Vaadin framework and to provide some basic tools for someone.");
        add(paragraph);
    }
}
