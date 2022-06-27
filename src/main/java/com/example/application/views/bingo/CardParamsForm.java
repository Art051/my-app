package com.example.application.views.bingo;

import com.example.application.data.Card;
import com.example.application.data.FileHandling;
import com.example.application.data.GamePlay;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardParamsForm extends VerticalLayout {

    private final BeanValidationBinder<Card> binder;
    private Card card;
    private final List<Card> cardsList = new ArrayList<>();
    private final GamePlay gamePlay = new GamePlay();
    private final FileHandling fileHandling = new FileHandling();

    //Fields relating to the user input form for setting card parameters
    private final IntegerField learningNum = new IntegerField("Learning number");
    private final IntegerField minMultiplication = new IntegerField("Minimum multiplication");
    private final IntegerField maxMultiplication = new IntegerField("Maximum multiplication");
    private final IntegerField cardCount = new IntegerField("Number of player cards");
    private final IntegerField cardSize = new IntegerField("Card size");
    private final Checkbox showFormulas = new Checkbox("Show formulas on player cards");
    private final Button clearButton = new Button("Clear");
    private final Button generateButton = new Button("Generate cards");

    //Fields for the deletion of files
    private final File downloadableFiles = new File(fileHandling.getGeneratedfilesDirString());
    private final DownloadLinksView linksArea = new DownloadLinksView(downloadableFiles);
    private final Button clearFilesButton = new Button("Clear files");
    private final Button yesButton = new Button("Yes");
    private final Button noButton = new Button("No");


    public CardParamsForm() throws IOException {
        binder = new BeanValidationBinder<>(Card.class);
        binder.bindInstanceFields(this);
        setParamsValues();
        add(createParamsForm());
        add(showFormulas);
        add(createButtonLayout());
        add(createFormProdNotif());
        add("Download Links:");
        Section section = new Section(linksArea);
        section.setMaxHeight("150px");
        Scroller scroller = new Scroller(section);
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        add(scroller);
        setClearFilesActions();
        add(clearFilesButton);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private FormLayout createParamsForm() {
        FormLayout paramsForm = new FormLayout();
        paramsForm.add(learningNum, minMultiplication, maxMultiplication, cardCount, cardSize);
        paramsForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        return paramsForm;
    }

    private void setParamsValues() {
        learningNum.setValue(1);
        learningNum.setMin(1);
        learningNum.setHasControls(true);

        minMultiplication.setValue(1);
        minMultiplication.setMin(1);
        minMultiplication.setHasControls(true);

        maxMultiplication.setValue(2);
        maxMultiplication.setMin(2);
        maxMultiplication.setHasControls(true);

        cardCount.setValue(1);
        cardCount.setMin(1);
        cardCount.setHasControls(true);

        cardSize.setValue(1);
        cardSize.setMin(1);
        cardSize.setHasControls(true);
        cardSize.setHelperText("Card size has to be equal to or lower than maximum multiplication");
        cardSize.addValueChangeListener(event -> {
            if (event.getValue() > maxMultiplication.getValue()) {
                cardSize.setValue(maxMultiplication.getValue());
            }
        });
        cardSize.setValueChangeMode(ValueChangeMode.EAGER);
    }

    private HorizontalLayout createButtonLayout() {
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clearButton.getStyle().set("margin-inline-end", "auto");
        clearButton.addClickListener(click -> {
            resetFormFields();
        });

        generateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        generateButton.addClickListener(e -> {
                    try {
                        if (fileHandling.getGeneratedfilesMasterDirFile().listFiles().length >= 10) {
                            Notification fileLimitNotif = new Notification();
                            Text fileLimitNotifText = new Text(
                                    "There is a limit of 10 files at a time, please delete the existing files before creating more.");
                            fileLimitNotif.setPosition(Notification.Position.MIDDLE);
                            Button acknowledgeButton = new Button("Acknowledge");
                            acknowledgeButton.addClickListener(click -> fileLimitNotif.close());
                            VerticalLayout fileLimitNotifLayout = new VerticalLayout(fileLimitNotifText, acknowledgeButton);
                            fileLimitNotifLayout.setAlignItems(Alignment.CENTER);
                            fileLimitNotif.add(fileLimitNotifLayout);
                            fileLimitNotif.open();
                        }
                        else {
                            cardsList.clear();
                            generateCards();
                            fileHandling.generateCSV(gamePlay);
                            linksArea.refreshFileLinks();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );

        generateButton.addClickShortcut(Key.ENTER);
        return new HorizontalLayout(clearButton, generateButton);
    }

    private void resetFormFields() {
        learningNum.setValue(1);
        minMultiplication.setValue(1);
        maxMultiplication.setValue(2);
        cardSize.setValue(1);
    }

    private void generateCards() throws ValidationException {
        this.card = new Card();
        binder.writeBean(this.card);
        card.setShowFormulas(!showFormulas.getValue());
        card.setCompleteTimesTable(card.getCompleteTimesTable());
        card.getRemainingOptions().addAll(card.getCompleteTimesTable());
        card.setCardPairs(card.getCardPairs());
        card.setCardSelections(card.getCardSelections());
        cardsList.add(card);
        gamePlay.setPlayerList(cardsList);
        for (int i = 0; i < cardCount.getValue(); i++) {
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

    private void setClearFilesActions() {
        Notification confirmDelete = new Notification(new Text("Are you sure you want to delete the files?"));
        VerticalLayout clearFilesLayout = new VerticalLayout();
        confirmDelete.setPosition(Notification.Position.MIDDLE);
        yesButton.setIcon(new Icon(VaadinIcon.CHECK_CIRCLE));
        noButton.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilesButton.addClickListener(clearClick -> {
                    confirmDelete.open();
                    noButton.addClickListener(noClick -> {
                        confirmDelete.close();
                    });
                    yesButton.addClickListener(yesClick -> {
                        fileHandling.deleteDirectoryContents(downloadableFiles);
                        try {
                            linksArea.refreshFileLinks();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        confirmDelete.close();
                    });
                    clearFilesLayout.add(noButton, yesButton);
                    clearFilesLayout.setAlignItems(Alignment.CENTER);
                    confirmDelete.add(clearFilesLayout);
                }
        );
    }

    private Notification createFormProdNotif() {
        boolean[] infoNotifHasShown = {false};

        Notification notification = new Notification();
        notification.addThemeVariants();

        Div statusText = new Div(new Text("Check to generate player cards with formulas, uncheck to generate player cards with products. \n" +
                "The teacher's card will have the opposite to the players' cards."));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(statusText, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        showFormulas.addValueChangeListener(click -> {
            if (!infoNotifHasShown[0]) {
                notification.open();
                infoNotifHasShown[0] = true;
            }
        });
        return notification;
    }
}