package com.example.application.data.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class FileHandling {

    File cardsCSV;
    FileWriter fileWriter;
    BufferedWriter buffWriter;

    public FileHandling() {
    }

    public void generateCSV(GamePlay game) throws IOException {
        cardsCSV = new File("cards.csv");
        fileWriter = new FileWriter(cardsCSV);
        buffWriter = new BufferedWriter(fileWriter);
        for(Card card : game.getPlayerList()) {
            String cardSelections = card.getCardSelections().stream()
                    .collect(Collectors.joining(","));
            buffWriter.append(cardSelections);
            buffWriter.flush();
            buffWriter.newLine();
        }
    }
}
