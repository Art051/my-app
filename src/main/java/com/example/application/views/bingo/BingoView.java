package com.example.application.views.bingo;

import com.example.application.data.entity.Card;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


@PageTitle("Maths | Bingo")
@Route(value = "maths/bingo", layout = MainLayout.class)
public class BingoView extends VerticalLayout {

    private BeanValidationBinder<Card> binder;
    private Card card;
    private List<String[]> completeTimesTable = new ArrayList<>();
    private Button clearButton = new Button("Clear");
    private Button generateCards = new Button("Generate cards");

    private IntegerField learningNum = new IntegerField("Learning Number");
    private IntegerField minMultiplication = new IntegerField("Minimum multiplication");
    private IntegerField maxMultiplication = new IntegerField("Maximum multiplication");
    private IntegerField cardSize = new IntegerField("Card size");
    private boolean showFormulas;
    int currentMulti;


    public BingoView() {
        setSpacing(false);

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

        cardSize.setValue(1);
        cardSize.setMin(1);
        cardSize.setHasControls(true);
        add(cardSize);

        FormLayout formLayout = new FormLayout(learningNum,
                minMultiplication, maxMultiplication, cardSize);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);


        ComboBox<String> formulaOrProductBox = new ComboBox<>("Show formulas");
        formulaOrProductBox.setItems("Formulas", "Products");
        formulaOrProductBox.setRequired(true);
        formulaOrProductBox.addValueChangeListener(e -> {
            if (e.getValue().equals("Formulas")) {
                showFormulas = true;
            }
            else if (e.getValue().equals("Products")) {
                showFormulas = false;
            }
        });

        add(formulaOrProductBox);

        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clearButton.getStyle().set("margin-inline-end", "auto");
        clearButton.addClickListener(click -> {
            resetFormFields();
        });

        binder = new BeanValidationBinder<>(Card.class);

        binder.bindInstanceFields(this);

        generateCards.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        generateCards.addClickListener(e -> {
            try {
                if (formulaOrProductBox.getValue().equals("Formulas")){
                    this.showFormulas = true;
                }
                else if (formulaOrProductBox.getValue().equals("Products")){
                    this.showFormulas = false;
                }
                if (this.card == null) {
                    this.card = new Card();
                }
//                binder.forField(showFormulas)
//                        .bind(Card::isShowFormulas,
//                                Card::setShowFormulas);
                binder.writeBean(this.card);
//                binder.bindInstanceFields(this);
                card.setCompleteTimesTable(card.getCompleteTimesTable());
                card.getRemainingOptions().addAll(card.getCompleteTimesTable());
                card.setCardPairs(card.getCardPairs());
                card.setCardSelections(card.getCardSelections());
                Notification.show("Card generated!");
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }

            System.out.println(completeTimesTable);
            System.out.println("Min multi " + minMultiplication.getValue());
            System.out.println("Max multi " + maxMultiplication.getValue());
            System.out.println(card.toString());
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(clearButton, generateCards);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(JustifyContentMode.START);

        add(buttonLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }

    private void resetFormFields() {
        learningNum.setValue(1);
        minMultiplication.setValue(1);
        maxMultiplication.setValue(2);
        cardSize.setValue(1);
    }
}
