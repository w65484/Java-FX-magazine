package com.example.projekt.controllers;

import com.example.projekt.database.DefaultDbAccessor;
import com.example.projekt.helpers.ViewCreator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controller for the Hello view, main view of application.
 */
public class HelloController {

    @FXML
    private AnchorPane listPane;
    @FXML
    private AnchorPane formPane;

    /**
     * Event handler for the navigation buttons.
     * Gets the name of the view to navigate from node's user data.
     * @param event Event object.
     * @throws IOException If view could not be created.
     */
    @FXML
    protected void navigateTo(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        String viewName = (String) node.getUserData();
        navigateTo(viewName);
    }

    /**
     * Initialize the controller class and navigates to products view.
     */
    @FXML
    protected void initialize() throws IOException {

        if(!DefaultDbAccessor.checkConnection()){
            showErrorPrompt("Błąd połączenia z bazą danych. Aplikacja do działania wymaga połączenia z bazą danych.");
            Platform.exit();
        }

        navigateTo("products");
    }

    /**
     * Navigates to the view with the given name and associated form.
     * Takes care of dependencies between views' controllers.
     * @param viewName Name of the view to navigate to.
     * @throws IOException If view could not be created.
     */
    private void navigateTo(String viewName) throws IOException {
        var listView = ViewCreator.CreateView(viewName);
        assert listView != null;
        var listController = (ListController<?>)listView.getUserData();
        listPane.getChildren().clear();
        listPane.getChildren().add(listView);

        var formView = ViewCreator.CreateView(viewName + "-form");
        assert formView != null;
        var formController = (FormController<?>)formView.getUserData();
        formController.setListController(listController);
        formPane.getChildren().clear();
        formPane.getChildren().add(formView);
    }

    /**
     * Shows error prompt.
     * @param message Message to show.
     */
    private void showErrorPrompt(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}