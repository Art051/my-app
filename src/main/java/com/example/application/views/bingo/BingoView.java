package com.example.application.views.bingo;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Maths | Bingo")
@Route(value = "maths/bingo", layout = MainLayout.class)
public class BingoView extends VerticalLayout {

    public BingoView() {
        setSpacing(false);

        IntegerField learningNum = new IntegerField("Learning Number");
        learningNum.setValue(1);
        learningNum.setMin(1);
        learningNum.setHasControls(true);
        add(learningNum);

        IntegerField minMultiplication = new IntegerField("Minimum multiplication");
        minMultiplication.setValue(1);
        minMultiplication.setMin(1);
        minMultiplication.setHasControls(true);
        add(minMultiplication);

        IntegerField maxMultiplication = new IntegerField("Maximum multiplication");
        maxMultiplication.setValue(2);
        maxMultiplication.setMin(2);
        maxMultiplication.setHasControls(true);
        add(maxMultiplication);

        IntegerField cardSize = new IntegerField("Card size");
        cardSize.setValue(5);
        cardSize.setMin(1);
        cardSize.setHasControls(true);
        add(cardSize);

        FormLayout formLayout = new FormLayout(learningNum,
                minMultiplication, maxMultiplication, cardSize);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);

        RadioButtonGroup<String> formulaOrProduct = new RadioButtonGroup<>();
        formulaOrProduct.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        formulaOrProduct.setLabel("Show formulas or products");
        formulaOrProduct.setItems("Formula", "Product");
        add(formulaOrProduct);

        Button clearButton = new Button("Clear");
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clearButton.getStyle().set("margin-inline-end", "auto");
        clearButton.addClickListener(click -> {
            learningNum.setValue(0);
            minMultiplication.setValue(0);
            maxMultiplication.setValue(0);
            cardSize.setValue(0);
        });

        Button generateCards = new Button("Generate cards");
        generateCards.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout(clearButton, generateCards);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(JustifyContentMode.START);

        add(buttonLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }
}
