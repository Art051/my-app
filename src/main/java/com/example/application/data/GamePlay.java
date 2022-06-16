package com.example.application.data;

import java.util.ArrayList;
import java.util.List;

public class GamePlay {

    private int learningNum;
    private int minMultiplication;
    private int maxMultiplication;
    private int cardSize;
    private int playerCount;
    private boolean teacherHasFormulas;
    private List<String[]> completeTimesTable = new ArrayList<>();

    private Card playerCard;
    private Card teacherCard = new Card();
    private List<Card> playerList = new ArrayList<>();

    public GamePlay(int learningNum, int minMultiplication, int maxMultiplication, int cardSize, int playerCount, boolean teacherHasFormulas) {
        this.learningNum = learningNum;
        this.minMultiplication = minMultiplication;
        this.maxMultiplication = maxMultiplication;
        this.cardSize = cardSize;
        this.playerCount = playerCount;
        this.teacherHasFormulas = teacherHasFormulas;
    }

    public GamePlay() {
    }

    public Card getPlayerCard() {
        return playerCard;
    }

    public Card generatePlayer() {
        playerCard = new Card();
        playerCard.setShowFormulas(!teacherHasFormulas);
        populateCard(playerCard);
        return playerCard;
    }

    public Card getTeacherCard() {
        return teacherCard;
    }

    public void setTeacherCard(Card teacherCard) {
        teacherCard.setShowFormulas(teacherHasFormulas);
        populateCard(teacherCard);
        this.teacherCard = teacherCard;
    }

    private void populateCard(Card card) {
        card.setLearningNum(learningNum);
        card.setMinMultiplication(minMultiplication);
        card.setMaxMultiplication(maxMultiplication);
        card.setCardSize(cardSize);
        card.getRemainingOptions().addAll(completeTimesTable);
        card.setCardPairs(card.getCardPairs());
        card.setCardSelections(card.getCardSelections());
    }

    public List<Card> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Card> playerList) {

        for (int i = 0; i < playerCount; i++) {
            playerList.add(generatePlayer());
        }
        this.playerList = playerList;
    }

    public void addPlayerCard(Card card){
        this.playerList.add(card);
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

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public List<String[]> getCompleteTimesTable() {
        return completeTimesTable;
    }

    public void setCompleteTimesTable(List<String[]> completeTimesTable) {
        int currentMulti = minMultiplication;
        for (int i = 0; i <= (maxMultiplication - minMultiplication); i++) {
            completeTimesTable.add(new String[]{
                    learningNum + " X " + currentMulti,
                    String.valueOf(learningNum * currentMulti)}
            );
            currentMulti++;
        }
        this.completeTimesTable = completeTimesTable;
    }
}
