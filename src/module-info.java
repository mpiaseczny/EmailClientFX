module EmailClientFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens com.mpiaseczny;
    opens com.mpiaseczny.controller;
//    opens com.mpiaseczny.model;
    opens com.mpiaseczny.view;
}