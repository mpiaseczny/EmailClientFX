package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.model.EmailMessage;
import com.mpiaseczny.model.EmailTreeItem;
import com.mpiaseczny.controller.services.MessageRendererService;
import com.mpiaseczny.model.SizeInteger;
import com.mpiaseczny.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends Controller implements Initializable {

    private MenuItem markAsUnReadMenuItem = new MenuItem("Mark As Unread");
    private MenuItem deleteMessageMenuItem = new MenuItem("Delete");

    @FXML
    public TreeView emailsTreeView;

    @FXML
    public TableView<EmailMessage> emailsTableView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    public WebView emailsWebView;

    private MessageRendererService messageRendererService;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    public void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    public void addAccountAction() {
        viewFactory.showLoginWindow();
    }

    @FXML
    public void createMessageAction() {
        viewFactory.showCreateMessageWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEmailsTreeView();
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRendererService();
        setUpMessageSelection();
        setUpContextMenus();
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }

    private void setUpEmailsTableView() {
        senderCol.setCellValueFactory(new PropertyValueFactory<>("sender"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<>("recipient"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory((TableColumn<EmailMessage, Date> column) -> {
            return new TableCell<EmailMessage, Date>() {
                @Override
                protected void updateItem(Date date, boolean empty) {
                    super.updateItem(date, empty);
                    SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                    if (date == null || empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(date));
                    }
                }
            };
        });

        emailsTableView.setContextMenu(new ContextMenu(markAsUnReadMenuItem, deleteMessageMenuItem));
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                emailManager.setSelectedFolder(item);
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            if(item.isRead()) {
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpMessageRendererService() {
        messageRendererService = new MessageRendererService(emailsWebView.getEngine());
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(event -> {
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null) {
                emailManager.setSelectedMessage(emailMessage);
                if(!emailMessage.isRead()) {
                    emailManager.setRead();
                }
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart(); //bc start can only be used once
            }
        });
    }

    private void setUpContextMenus() {
        markAsUnReadMenuItem.setOnAction(event -> {
            emailManager.setUnRead();
        });
        deleteMessageMenuItem.setOnAction(event -> {
            emailManager.deleteSelectedMessage();
            emailsWebView.getEngine().loadContent("");
        });
    }
}
