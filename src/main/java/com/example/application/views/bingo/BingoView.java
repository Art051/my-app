package com.example.application.views.bingo;

import com.example.application.data.entity.Card;
import com.example.application.data.entity.FileHandling;
import com.example.application.data.entity.GamePlay;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Aside;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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

        Div pageDescription = new Div();
        pageDescription.add(new Paragraph("Welcome to the Bingo card generator! \n To create a series of individual cards for players, please complete all fields." +
                "The cards will then be generated in the form of tables which can be printed and cut-out as individual cards."));
        add(pageDescription);


        setFieldDefaultVals();
        FormLayout formLayout = new FormLayout(learningNum,
                minMultiplication, maxMultiplication, cardCount, cardSize);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);

        boolean[] infoNotifHasShown = {false};
        showFormulas.addValueChangeListener(click -> {
            if (!infoNotifHasShown[0]){
                createFormProdNotif();
                infoNotifHasShown[0] = true;
            }
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

    private void createFormProdNotif(){
        // When creating a notification using the constructor,
// the duration is 0-sec by default which means that
// the notification does not close automatically.

            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

            Div statusText = new Div(new Text("Check to generate cards with formulas, uncheck to generate cards with products."));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(statusText, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            notification.add(layout);
            notification.open();
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
    }

    private void resetFormFields() {
        learningNum.setValue(1);
        minMultiplication.setValue(1);
        maxMultiplication.setValue(2);
        cardSize.setValue(1);
    }
}
