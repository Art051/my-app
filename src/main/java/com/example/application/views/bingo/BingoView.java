package com.example.application.views.bingo;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.IOException;


@PageTitle("Maths | Bingo")
@Route(value = "maths/bingo", layout = MainLayout.class)
public class BingoView extends VerticalLayout {

    private CardParamsForm cardParamsForm = new CardParamsForm();
    private Paragraph paragraph;


    public BingoView() throws IOException {
        setSpacing(false);
        paragraph = new Paragraph();
        paragraph.add("Welcome to the Bingo card generator!");
        add(paragraph);
        paragraph = new Paragraph();
        paragraph.add("To create a series of individual cards for players, please complete all fields. \n" +
                "The cards will then be generated as a CSV and PDF file which can be printed and cut-out as individual cards.");
        add(paragraph);

        add(cardParamsForm);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
