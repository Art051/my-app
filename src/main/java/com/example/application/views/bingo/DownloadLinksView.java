package com.example.application.views.bingo;

import com.example.application.data.FileHandling;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.*;

public class DownloadLinksView extends VerticalLayout {

    private final File uploadFolder;


    public DownloadLinksView(File uploadFolder) throws IOException {
        this.uploadFolder = uploadFolder;
        FileHandling fileHandling = new FileHandling();
        fileHandling.generateFilesDirectory(uploadFolder.getName());
        refreshFileLinks();
        setMargin(true);
    }

    public void refreshFileLinks() throws IOException {
        removeAll();
        for (File file : uploadFolder.listFiles()) {
            addLinkToFile(file);
        }
    }

    private void addLinkToFile(File file) {
        StreamResource streamResource = new StreamResource(file.getName(), () -> getStream(file));
        Anchor link = new Anchor(streamResource, String.format("%s (%d KB)", file.getName(),
                (int) file.length() / 1024));
        link.getElement().setAttribute("download", true);
        add(link);
    }

    private InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stream;
    }
}