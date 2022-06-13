package com.example.application.views.bingo;

import com.example.application.data.entity.Card;
import com.example.application.data.entity.FileHandling;
import com.example.application.data.entity.GamePlay;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@PageTitle("Maths | Bingo")
@Route(value = "maths/bingo", layout = MainLayout.class)
public class BingoView extends VerticalLayout {

    private final BeanValidationBinder<Card> binder;
    private Card card;
    private final List<Card> cardsList = new ArrayList<>();
    private final GamePlay gamePlay = new GamePlay();
    private final FileHandling fileHandling = new FileHandling();
    private final Button clearButton = new Button("Clear");
    private final Button generateCards = new Button("Generate cards");
    private final IntegerField learningNum = new IntegerField("Learning Number");
    private final IntegerField minMultiplication = new IntegerField("Minimum multiplication");
    private final IntegerField maxMultiplication = new IntegerField("Maximum multiplication");
    private final IntegerField cardCount = new IntegerField("Number of player cards");
    private final IntegerField cardSize = new IntegerField("Card size");
    private final Checkbox showFormulas = new Checkbox("Show formulas");


    public BingoView() {
        setSpacing(false);

        binder = new BeanValidationBinder<>(Card.class);
        binder.bindInstanceFields(this);

        setFieldDefaultVals();
        FormLayout formLayout = new FormLayout(learningNum,
                minMultiplication, maxMultiplication, cardCount, cardSize);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        showFormulas.addValueChangeListener(click -> {
            Notification.show("Check to generate cards with formulas, uncheck to generate cards with products.",
                    5000,
                    Notification.Position.BOTTOM_END);
        });

        add(showFormulas);

        createClearButton();
        createGeneratorButton();
        HorizontalLayout buttonLayout = new HorizontalLayout(clearButton, generateCards);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(JustifyContentMode.START);
        add(buttonLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void createClearButton() {
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clearButton.getStyle().set("margin-inline-end", "auto");
        clearButton.addClickListener(click -> {
            resetFormFields();
        });
    }

    private void createGeneratorButton() {
        generateCards.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        generateCards.addClickListener(e -> {
            try {
                cardsList.clear();
                generateCards();
                fileHandling.generateCSV(gamePlay);
            }
            catch (ValidationException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setFieldDefaultVals() {
        learningNum.setValue(1);
        learningNum.setMin(1);
        learningNum.setHasControls(true);
        add(learningNum);

        minMultiplication.setValue(1);
        minMultiplication.setMin(1);
        minMultiplication.setHasControls(true);
        add(minMultiplication);

        maxMultiplication.setValue(2);
        maxMultiplication.setMin(2);
        maxMultiplication.setHasControls(true);
        add(maxMultiplication);

        cardCount.setValue(1);
        cardCount.setMin(1);
        cardCount.setHasControls(true);
        add(cardCount);

        cardSize.setValue(1);
        cardSize.setMin(1);
        cardSize.setHasControls(true);
        add(cardSize);
    }

    private void generateCards() throws ValidationException {
        for(int i = 0; i < cardCount.getValue(); i++) {
            this.card = new Card();
            binder.writeBean(this.card);
            card.setCompleteTimesTable(card.getCompleteTimesTable());
            card.getRemainingOptions().addAll(card.getCompleteTimesTable());
            card.setCardPairs(card.getCardPairs());
            card.setCardSelections(card.getCardSelections());
            cardsList.add(card);
            gamePlay.setPlayerList(cardsList);
        }
        Notification.show("Generation complete!");
    }

    private void resetFormFields() {
        learningNum.setValue(1);
        minMultiplication.setValue(1);
        maxMultiplication.setValue(2);
        cardSize.setValue(1);
    }
}
