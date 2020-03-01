package com.mpiaseczny;

import com.mpiaseczny.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("view/LoginWindow.fxml"));
//        primaryStage.setTitle("Email Client FX");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
