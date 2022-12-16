package com.example.projekt.controllers;

import com.example.projekt.interfaces.IEditAction;
import com.example.projekt.database.DefaultDbAccessor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Abstract controller for the list view.
 * @param <T> The type of the list items.
 */
public abstract class ListController<T> implements ChangeListener<String>{

    /**
     * Event handler for edit action which should be called in form.
     */
    public IEditAction<T> edit;
    /**
     * Items of the list as ObservableList of type <b>T<b/>.
     */
    private final ObservableList<T> items = FXCollections.observableArrayList();

    /**
     * Reference to the table view.
     */
    public TableView<T> tableView;
    /**
     * Database accessor.
     */
    protected final DefaultDbAccessor<T> model;

    /**
     * Reference to search text field.
     */
    @FXML
    public TextField searchField;

    /**
     * Constructor.
     * @param clazz class object of entity which we want use in controller.
     */
    protected ListController(Class<T> clazz) {
        model = new DefaultDbAccessor<>(clazz);
    }

    /**
     * Initialize method.
     * Calls reload to fill the table view.
     */
    @FXML
    protected void initialize(){
        searchField.textProperty().addListener(this);
        reload();
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        reload();
    }

    /**
     * Reloads items in table view from database.
     */
    public void reload() {
        items.clear();
        items.addAll(model.getAll(searchField.getText()));
    }
    /**
     * Getter for items.
     */
    @FXML
    public ObservableList<T> getItems() {
        return items;
    }

    /**
     * Creates and returns ContextMenu for table view with edit and delete actions.
     */
    @FXML
    public ContextMenu getContextMenu() {
        var menu = new ContextMenu();
        var edit = new MenuItem("Edytuj");
        var delete = new MenuItem("Usuń");
        edit.setOnAction(this::edit);
        delete.setOnAction(this::delete);
        menu.getItems().addAll(edit, delete);
        return menu;
    }

    /**
     * Event handler for edit action.
     * @param event ActionEvent.
     */
    private void edit(ActionEvent event) {
        T item = tableView.getSelectionModel().getSelectedItem();

        if(edit != null)
            edit.edit(item);
    }

    /**
     * Event handler for delete action.
     * @param event ActionEvent.
     */
    private void delete(ActionEvent event) {
        T item = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.WARNING, "Czy na pewno chcesz usunąć wiersz?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            var successfull = model.delete(item);
            if(!successfull) {
                this.showErrorPrompt("Nie można usunąć kategori/producenta ponieważ jest przypisany do co najmniej jednego produktu");
            }
        }

        reload();
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
