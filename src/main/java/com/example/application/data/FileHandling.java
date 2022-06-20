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
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class FileHandling {

    private FileWriter fileWriter;
    private BufferedWriter buffWriter;
    private Calendar calendar;
    private File file;
    private final String generatedfilesMasterDir = "generated-files";
    private String filePrefix = "Bingo cards";
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

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

        for(Card card : game.getPlayerList()) {
            String cardSelections = card.getCardSelections().stream()
                    .collect(Collectors.joining(","));
            buffWriter.append(cardSelections);
            buffWriter.flush();
            buffWriter.newLine();
        }

        generatePDF(csvFile.getPath(),generatedfilesMasterDir +  "/" + filePrefix + " - " + formatter.format(calendar.getTime()) + ".pdf");
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

        Table table = new Table(UnitValue.createPercentArray(5), true);
        doc.add(table);
        String line;
        while ((line = br.readLine()) != null) {
            table.addCell(new Cell().add(new Paragraph("Player: ")).setBold());
            table.startNewRow();
            StringTokenizer tokenizer = new StringTokenizer(line, ",");
            while (tokenizer.hasMoreTokens()) {
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                table.addCell(cell)
                        .setMargins(0, 0, 0, 0);
            }
            table.startNewRow();
        }
        table.complete();

        doc.close();
    }

    public String getGeneratedfilesMasterDir() {
        return generatedfilesMasterDir;
    }
}
