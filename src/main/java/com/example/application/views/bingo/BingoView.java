package com.example.application.views.bingo;

import com.example.application.data.entity.Card;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;


@Route(value = "maths/bingo")
@PageTitle("Maths | Bingo")
public class BingoView extends VerticalLayout{

    Grid<Card> cardsGrid = new Grid<>(Card.class);
    TextField filterText = new TextField();
    BingoCardForm bingoCardForm;


    public BingoView() {

        addClassName("bingo-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getContent());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(cardsGrid, bingoCardForm);
        content.setFlexGrow(2, cardsGrid);


        content.setFlexGrow(1, bingoCardForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        bingoCardForm = new BingoCardForm(Collections.emptyList());


        bingoCardForm.setWidth("25em");
    }

    private void configureGrid() {
        cardsGrid.addClassNames("bingo-cards-grid");
        cardsGrid.setSizeFull();
        cardsGrid.setColumns("cardNum", "formula", "product");
        cardsGrid.addColumn(Card::getCardNumber).setHeader("Card number");
        cardsGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    }
}
