package com.example.application.views.bingo;

import com.example.application.data.entity.Card;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import java.util.List;

public class BingoCardForm extends FormLayout {

    IntegerField learningNumber = new IntegerField("Learning number");
    IntegerField minimumMultiplication = new IntegerField("Minimum multiplication");
    IntegerField maximumMultiplication = new IntegerField("Maximum multiplication");
    IntegerField cardSize = new IntegerField("Card size");


    Button save = new Button("Generate card");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public BingoCardForm(List<Card> bingoCards) {
        addClassName("bingo-form");

        add(learningNumber,
                minimumMultiplication,
                maximumMultiplication,
                cardSize,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);

        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);


    }
}