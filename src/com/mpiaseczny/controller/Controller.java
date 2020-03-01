package com.mpiaseczny.controller;

import com.mpiaseczny.EmailManager;
import com.mpiaseczny.view.ViewFactory;

public abstract class Controller {

    protected EmailManager emailManager;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public Controller(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
