package com.mpiaseczny.view;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.controller.Controller;
import com.mpiaseczny.controller.LoginWindowController;
import com.mpiaseczny.controller.MainWindowController;
import com.mpiaseczny.controller.OptionsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void showLoginWindow() {
        Controller controllerLogin = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        initializeStage(controllerLogin);
    }

    public void showMainWindow() {
        Controller controllerMain = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controllerMain);
    }

    public void showOptionsWindow() {
        Controller controllerOptions = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        initializeStage(controllerOptions);
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
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }
}
