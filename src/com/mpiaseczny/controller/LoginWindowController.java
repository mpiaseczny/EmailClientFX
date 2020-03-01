package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends Controller {

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginErrorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonOnClick() {
        System.out.println("Email: " + emailAddressField.getText() + ". Password: " + passwordField.getText() + ".");
        viewFactory.showMainWindow();
        Stage stage = (Stage) loginErrorLabel.getScene().getWindow();
        viewFactory.closeStage(stage);
    }
}
