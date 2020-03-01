package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainWindowController extends Controller {

    @FXML
    public TreeView emailsTreeView;

    @FXML
    public TableView emailsTableView;

    @FXML
    public WebView emailsWebView;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    public void optionsAction() {
        viewFactory.showOptionsWindow();
    }
}
