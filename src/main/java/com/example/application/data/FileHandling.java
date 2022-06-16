package com.example.application.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.Collectors;

public class FileHandling {

    private File cardsCSV;
    private File cardsDirectory =  new File("src/main/resources/downloadfiles");
    private FileWriter fileWriter;
    private BufferedWriter buffWriter;
    private Calendar calendar;

    public FileHandling() {
    }


    public void generateCSV(GamePlay game) throws IOException {
        calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String filename = "Bingo cards";
        cardsCSV = new File(cardsDirectory + filename + formatter.format(calendar.getTime()) + ".csv");
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

    public void deleteDirectory(File file)
    {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}
