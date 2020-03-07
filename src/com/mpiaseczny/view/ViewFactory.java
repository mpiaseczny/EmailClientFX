package com.mpiaseczny.view;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {

    private List<Stage> activeStages;
    private EmailManager emailManager;
    private boolean mainWindowInitialized = false;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<>();
    }

    // View options handling:
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public boolean isMainWindowInitialized() {
        return mainWindowInitialized;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void showLoginWindow() {
        Controller controllerLogin = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        initializeStage(controllerLogin);
    }

    public void showMainWindow() {
        Controller controllerMain = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controllerMain);
        mainWindowInitialized = true;
    }

    public void showOptionsWindow() {
        Controller controllerOptions = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        initializeStage(controllerOptions);
    }

    public void showCreateMessageWindow() {
        Controller controllerCreateMessage = new CreateMessageController(emailManager, this, "CreateMessageWindow.fxml");
        initializeStage(controllerCreateMessage);
    }

    private void initializeStage(Controller controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Loading scene error: " + e.getMessage());
            return;
        }

        Scene scene = new Scene(parent);
        updateStyle(scene);
        Stage stage = new Stage();
        stage.setScene(scene);
        String fxmlName = controller.getFxmlName();
        switch(fxmlName) {
            case "LoginWindow.fxml":
                stage.setTitle("Login to Email Account");
                break;
            case "MainWindow.fxml":
                stage.setTitle("Email Client FX");
                break;
            case "OptionsWindow.fxml":
                stage.setTitle("Options");
                break;
            case "CreateMessageWindow.fxml":
                stage.setTitle("New Message");
                break;
        }
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void updateAllStyles() {
        for (Stage stage: activeStages) {
            Scene scene = stage.getScene();
            updateStyle(scene);
        }
    }

    private void updateStyle(Scene scene){
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
        scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
    }
}
