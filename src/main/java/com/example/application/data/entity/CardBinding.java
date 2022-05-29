package com.example.application.data.entity;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route("binding-overview")
public class CardBinding extends VerticalLayout {

    public CardBinding() {


        // Data model
        Card bingoCard = new Card();

        // Two editors: the actual editor and read-only display
        CardEditor editor1 = new CardEditor(false);
        CardEditor editor2 = new CardEditor(true);

        // The editor and buttons
        VerticalLayout layout1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Save");
        saveButton.addClickListener(click ->

        {
            try {
                editor1.getBinder().writeBean(bingoCard);
                editor2.getBinder().readBean(bingoCard);
            } catch (ValidationException e) {
            }
        });
        Button resetButton = new Button("Reset");
        resetButton.addClickListener(click ->

        {
            editor1.getBinder().readBean(bingoCard);
        });
        buttons.add(saveButton, resetButton);
        layout1.add(editor1, buttons);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.add(editor2);

        // Show the two editors side-by-side
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.START);
        hlayout.add(layout1, layout2);

        add(hlayout);
    }
}
