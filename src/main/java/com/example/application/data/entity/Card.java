package com.example.application.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card {

    private int cardNumber;
    private int learningNum;
    private int minMultiplication;
    private int maxMultiplication;
    private int cardSize;
    private boolean showFormulas;
    private List<String[]> completeTimesTable = new ArrayList<>();
    private List<String[]> remainingOptions = new ArrayList<>();
    private List<String[]> cardPairs = new ArrayList<>();
    private List<String> cardSelections = new ArrayList<>();

    protected final Random random = new Random();

    public Card() {
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getLearningNum() {
        return learningNum;
    }

    public void setLearningNum(int learningNum) {
        this.learningNum = learningNum;
    }

    public int getMinMultiplication() {
        return minMultiplication;
    }

    public void setMinMultiplication(int minMultiplication) {
        this.minMultiplication = minMultiplication;
    }

    public int getMaxMultiplication() {
        return maxMultiplication;
    }

    public void setMaxMultiplication(int maxMultiplication) {
        this.maxMultiplication = maxMultiplication;
    }

    public int getCardSize() {
        return cardSize;
    }

    public void setCardSize(int cardSize) {
        this.cardSize = cardSize;
    }

    public boolean isShowFormulas() {
        return showFormulas;
    }

    public void setShowFormulas(boolean showFormulas) {
        this.showFormulas = showFormulas;
    }

    public List<String[]> getRemainingOptions() {
        return remainingOptions;
    }

    public void setRemainingOptions(List<String[]> remainingOptions) {
        this.remainingOptions = completeTimesTable;
    }

    public List<String[]> getCompleteTimesTable() {
        return completeTimesTable;
    }

    public void setCompleteTimesTable(List<String[]> completeTimesTable) {
        int currentMulti = minMultiplication;
        for(int i = 0; i <= (maxMultiplication - minMultiplication); i++){
            completeTimesTable.add(new String[] {
                    learningNum + " X " + currentMulti,
                    String.valueOf(learningNum * currentMulti)}
            );
            currentMulti++;
        }
        this.completeTimesTable = completeTimesTable;
    }

    public List<String[]> getCardPairs() {
        return cardPairs;
    }

    public void setCardPairs(List<String[]> cardPairs) {

        for (int i = 0; i < cardSize; i++) {
            int currentSelection = random.nextInt(remainingOptions.size());
            cardPairs.add(remainingOptions.get(currentSelection));
            remainingOptions.remove(currentSelection);
        }
        this.cardPairs = cardPairs;
    }

    public List<String> getCardSelections() {
        return cardSelections;
    }

    public void setCardSelections(List<String> cardSelections) {
        for (int i = 0; i < cardSize; i++) {
            String[] currentPair = cardPairs.get(i);
            if (showFormulas) {
                cardSelections.add(currentPair[0]);
            } else {
                cardSelections.add(currentPair[1]);
            }
        }
        this.cardSelections = cardSelections;
    }

}