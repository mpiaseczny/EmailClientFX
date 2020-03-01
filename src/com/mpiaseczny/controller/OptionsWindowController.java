package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class OptionsWindowController extends Controller {

    @FXML
    private Slider fondSizePicker;

    @FXML
    private ChoiceBox<?> themePicker;

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void cancelButtonAction() {

    }

    @FXML
    void okButtonAction() {

    }
}
