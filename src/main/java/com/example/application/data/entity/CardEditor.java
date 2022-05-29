package com.example.application.data.entity;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Objects;

public class CardEditor extends FormLayout {

    private Binder<Card> binder;

    public CardEditor(boolean readOnly) {
        // For binding the form to the data model
        binder = new Binder<>(Card.class);

        IntegerField learningNum = new IntegerField();
        addFormItem(learningNum, "Learning Number");
        binder.bind(learningNum, Card::getLearningNum,
                Card::setLearningNum);
        learningNum.setReadOnly(readOnly);

        IntegerField minMultiplication = new IntegerField();
        addFormItem(minMultiplication, "Minimum multiplication");
        binder.bind(minMultiplication, Card::getMinMultiplication,
                Card::setMinMultiplication);
        minMultiplication.setReadOnly(readOnly);


        IntegerField maxMultiplication = new IntegerField();
        addFormItem(maxMultiplication, "Maximum multiplication");
        binder.bind(maxMultiplication, Card::getMinMultiplication,
                Card::setMinMultiplication);
        maxMultiplication.setReadOnly(readOnly);


        IntegerField cardSize = new IntegerField();
        addFormItem(cardSize, "Maximum multiplication");
        binder.bind(cardSize, Card::getCardSize,
                Card::setCardSize);
        cardSize.setReadOnly(readOnly);

        RadioButtonGroup<String> selectFormulaOrProduct = new RadioButtonGroup<>();

        selectFormulaOrProduct.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        selectFormulaOrProduct.setLabel("Travel class");
        selectFormulaOrProduct.setItems("Formulas", "Products");
        addFormItem(selectFormulaOrProduct, "Display formulas or products");
        boolean showFormula = selectFormulaOrProduct.getValue().equals("Formulas");

        binder.bind(showFormula, Card::isShowFormula,
                Card::setShowFormula);
        selectFormulaOrProduct.setReadOnly(readOnly);
    }

    public Binder<Card> getBinder() {return binder;}
}