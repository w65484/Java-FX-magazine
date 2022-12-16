package com.example.projekt.controllers;

import com.example.projekt.database.entities.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller for the category form.
 */
public class CategoryFormController extends FormController<Category> {

    /**
     * FXML nodes used in form.
     */
    @FXML
    private Label infoLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;

    /**
     * Initializes the controller class.
     */
    public CategoryFormController() {
        super(Category.class);
    }

    /**
     * @see FormController#formFromModel(Object)
     */
    @Override
    protected void formFromModel(Category item) {
        this.name.setText(item == null ? "" : item.getName());
        this.description.setText(item == null ? "" : item.getDescription());
    }

    /**
     * @see FormController#modelFromForm(Object)
     */
    @Override
    protected Category modelFromForm(Category item) {
        if(item == null) item = new Category();
        item.setName(this.name.getText());
        item.setDescription(this.description.getText());
        return item;
    }

    /**
     * @see FormController#validate()
     */
    @Override
    protected boolean validate() {
        return !this.name.getText().isEmpty() &&
                !this.description.getText().isEmpty();
    }

    /**
     * Getters and setters.
     */
    @Override
    protected void setInfoLabel(boolean edit) {
        this.infoLabel.setText(edit ? "Edytuj kategorię" : "Dodaj kategorię");
    }

    @Override
    protected void showError() {
        this.errorLabel.setVisible(true);
    }

    @Override
    protected void hideError() {
        this.errorLabel.setVisible(false);
    }
}
