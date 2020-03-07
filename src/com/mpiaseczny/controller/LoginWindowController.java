package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.controller.services.LoginService;
import com.mpiaseczny.model.EmailAccount;
import com.mpiaseczny.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends Controller implements Initializable {

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
    public void loginButtonOnClick() {
        if(fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();

                switch(emailLoginResult) {
                    case SUCCESS:
                        System.out.println("Login successful " + emailAccount);
                        if(!viewFactory.isMainWindowInitialized()){
                            viewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) loginErrorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        loginErrorLabel.setText("Invalid credentials!");
                        return;
                    case FAILED_BY_NETWORK:
                        loginErrorLabel.setText("Network error!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        loginErrorLabel.setText("Unexpected error!");
                        return;
                    default:
                        return;
                }
            });
        }
    }

    private boolean fieldsAreValid() {
        if(emailAddressField.getText().isEmpty()) {
            loginErrorLabel.setText("Please fill email field.");
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            loginErrorLabel.setText("Please fill password field.");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // possibility to assign field values on stage initialization
        emailAddressField.setText("");
        passwordField.setText("");
    }
}
