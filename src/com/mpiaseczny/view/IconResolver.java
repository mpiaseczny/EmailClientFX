package com.mpiaseczny.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver {

    public Node getIconForFolder(String folderName) {
        String folderNameLowerCase = folderName.toLowerCase();
        ImageView imageView;
        try {
            if(folderNameLowerCase.contains("@")) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/email.png")));
            } else if (folderNameLowerCase.contains("wysłane")) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/sent1.png")));
            } else if (folderNameLowerCase.contains("inbox")) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/inbox.png")));
            } else if (folderNameLowerCase.contains("spam")) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/spam.png")));
            } else {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/folder.png")));
            }
        } catch(Exception e) {
            return null;
        }
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        return imageView;
    }
}
