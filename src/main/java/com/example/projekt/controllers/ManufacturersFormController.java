package com.example.projekt.controllers;

import com.example.projekt.database.entities.Manufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

/**
 * Controller for the manufacturer form.
 */
public class ManufacturersFormController extends FormController<Manufacturer> {

    /**
     * FXML nodes used in form.
     */
    @FXML
    public Label infoLabel;
    @FXML
    public Label errorLabel;
    @FXML
    public TextField address;
    @FXML
    public TextField slogan;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;

    /**
     * Initializes the controller class.
     */
    public ManufacturersFormController() {
        super(Manufacturer.class);
    }

    /**
     * @see FormController#formFromModel(Object)
     */
    @Override
    protected void formFromModel(Manufacturer item) {
        this.name.setText(item == null ? "" : item.getName());
        this.description.setText(item == null ? "" : item.getDescription());
        this.address.setText(item == null ? "" : item.getAddress());
        this.slogan.setText(item == null ? "" : item.getSlogan());
    }

    /**
     * @see FormController#modelFromForm(Object)
     */
    @Override
    protected Manufacturer modelFromForm(Manufacturer item) {
        if(item == null) item = new Manufacturer();
        item.setName(this.name.getText());
        item.setDescription(this.description.getText());
        item.setAddress(this.address.getText());
        item.setSlogan(this.slogan.getText());
        item.setCreated( item.getCreated() == null ? LocalDate.now() : item.getCreated());
        return item;
    }

    /**
     * @see FormController#validate()
     */
    @Override
    protected boolean validate() {
        return !name.getText().isEmpty() &&
                !description.getText().isEmpty() &&
                !address.getText().isEmpty() &&
                !slogan.getText().isEmpty();
    }

    /**
     * Getters and setters.
     */
    @Override
    protected void setInfoLabel(boolean edit) {
        this.infoLabel.setText(edit ? "Edytuj producenta" : "Dodaj producenta");
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
