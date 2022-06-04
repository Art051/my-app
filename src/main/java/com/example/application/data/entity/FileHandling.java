package com.example.application.data.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class FileHandling {

    File cardsCSV = new File("cards.csv");
    FileWriter fileWriter = new FileWriter(cardsCSV);
    BufferedWriter buffWriter = new BufferedWriter(fileWriter);

    public FileHandling() throws IOException {
    }

    void generateCSV(GamePlay game, BufferedWriter buffWriter) throws IOException {
        for(Card card : game.getPlayerList()) {
            String cardSelections = card.getCardSelections().stream()
                    .collect(Collectors.joining(","));
            buffWriter.append(cardSelections);
            buffWriter.flush();
            buffWriter.newLine();
        }
    }
}
