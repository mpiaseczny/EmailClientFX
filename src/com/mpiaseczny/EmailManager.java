package com.mpiaseczny;

import com.mpiaseczny.controller.services.FetchFolderService;
import com.mpiaseczny.controller.services.FolderUpdaterService;
import com.mpiaseczny.model.EmailAccount;
import com.mpiaseczny.model.EmailMessage;
import com.mpiaseczny.model.EmailTreeItem;
import com.mpiaseczny.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();

    private IconResolver iconResolver = new IconResolver();

    public ObservableList<EmailAccount> getEmailAccounts() {
        return emailAccounts;
    }

    private FolderUpdaterService folderUpdaterService;
    //Folder handling:
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    private List<Folder> folderList = new ArrayList<>();

    public EmailManager() {
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount) {
        emailAccounts.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        treeItem.setGraphic(iconResolver.getIconForFolder(emailAccount.getAddress()));
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);
        fetchFolderService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() {
        try {
          selectedMessage.setRead(true);
          selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
          selectedFolder.decrementUnreadMessagesCount();
        } catch(MessagingException e) {
            e.printStackTrace();
        }
    }

    public void setUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementUnreadMessagesCount();
        } catch(MessagingException e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch(MessagingException e) {
            e.printStackTrace();
        }
    }
}
