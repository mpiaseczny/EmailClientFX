package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.controller.services.EmailSenderService;
import com.mpiaseczny.model.EmailAccount;
import com.mpiaseczny.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateMessageController extends Controller implements Initializable {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label sendErrorLabel;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    public CreateMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }

    @FXML
    public void sendButtonAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText());
        emailSenderService.start();
        emailSenderService.setOnSucceeded(event -> {
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            switch(emailSendingResult) {
                case SUCCESS:
                    //Stage stage = (Stage) recipientTextField.getScene().getWindow();
                    //viewFactory.closeStage(stage);
                    sendErrorLabel.setText("Message sent. You can close the window.");
                    break;
                case FAILED_BY_PROVIDER:
                    sendErrorLabel.setText("Provider error!");
                    break;
                case FAILED_BY_UNEXPECTED_ERROR:
                    sendErrorLabel.setText("Unexpected error!");
                    break;
            }
        });
    }
}
