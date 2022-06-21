package com.example.application.data;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class FileHandling {

    private FileWriter fileWriter;
    private BufferedWriter buffWriter;
    private Calendar calendar;
    private File file;
    private StringTokenizer tokenizer;
    private final String generatedfilesMasterDir = "generated-files";
    private final String filePrefix = "Bingo cards";
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public FileHandling() {
    }

    public File generateFilesDirectory(String parentDir) {
        file = new File(parentDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public void generateCSV(GamePlay game) throws Exception {
        calendar = Calendar.getInstance();
        File csvFile = new File(getGeneratedfilesMasterDir() + "/" + filePrefix + " - " + formatter.format(calendar.getTime()) + ".csv");
        fileWriter = new FileWriter(csvFile);
        buffWriter = new BufferedWriter(fileWriter);

        for (Card card : game.getPlayerList()) {
            String cardSelections = card.getCardSelections().stream()
                    .collect(Collectors.joining(","));
            buffWriter.append(cardSelections);
            buffWriter.flush();
            buffWriter.newLine();
        }
        generatePDF(csvFile.getPath(), generatedfilesMasterDir + "/" + filePrefix + " - " + formatter.format(calendar.getTime()) + ".pdf");
    }

    public void deleteDirectoryContents(File file) {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectoryContents(subfile);
            }
            subfile.delete();
        }
    }

    public void generatePDF(String csvDataSource, String pdfDataDest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(pdfDataDest));
        Document doc = new Document(pdfDoc);
        BufferedReader br = new BufferedReader(new FileReader(csvDataSource));

        List<String> list;
        list = br.lines().collect(Collectors.toList());

        Table table = new Table(UnitValue.createPercentArray(5), true);
        doc.add(table);
        String line;

        for (int i = 0; i < list.size(); i++) {
            line = list.get(i);
            if (i == 0) {
                createTableCardRows(table, line, "Teacher");
            }
            else {
                createTableCardRows(table, line, "Player");
            }
        }
        table.complete();
        doc.close();
    }

    private void createTableCardRows(Table table, String line, String cardOwner) {
        table.addCell(new Cell().add(new Paragraph(cardOwner)).setBold());
        table.startNewRow();
        tokenizer = new StringTokenizer(line, ",");
        while (tokenizer.hasMoreTokens()) {
            Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
            table.addCell(cell)
                    .setMargins(0, 0, 0, 0);
        }
        table.startNewRow();
    }

    public String getGeneratedfilesMasterDir() {
        return generatedfilesMasterDir;
    }
}
