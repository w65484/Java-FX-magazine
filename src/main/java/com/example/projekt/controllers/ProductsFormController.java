package com.example.projekt.controllers;

import com.example.projekt.cellFactories.CategoryComboBoxCellFactory;
import com.example.projekt.cellFactories.ManufacturerComboBoxCellFactory;
import com.example.projekt.database.entities.Category;
import com.example.projekt.database.entities.Manufacturer;
import com.example.projekt.database.entities.Product;
import com.example.projekt.database.DefaultDbAccessor;
import com.example.projekt.helpers.ComboBoxAutocomplete;
import com.example.projekt.validators.FloatValidator;
import com.example.projekt.validators.IntegerValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

/**
 * Controller for the products form.
 */
public class ProductsFormController extends FormController<Product> {

    private final ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    /**
     * FXML nodes used in form.
     */
    @FXML
    public Label infoLabel;
    @FXML
    public Label errorLabel;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    private CheckBox available;
    @FXML
    private ComboBox<Category> category;
    @FXML
    private ComboBox<Manufacturer> manufacturer;

    /**
     * Initializes the controller class.
     */
    public ProductsFormController() {
        super(Product.class);
    }

    /**
     * Initializes the form.
     */
    @FXML
    protected void initialize(){
        manufacturers.clear();
        categories.clear();
        manufacturers.addAll(new DefaultDbAccessor<>(Manufacturer.class).getAll("", 5));
        categories.addAll(new DefaultDbAccessor<>(Category.class).getAll("", 5));

        category.setItems(categories);
        manufacturer.setItems(manufacturers);

        ComboBoxAutocomplete.autoCompleteComboBox(category,
                (typedText) ->{
                    ObservableList<Category> items = FXCollections.observableArrayList();
                    items.addAll(new DefaultDbAccessor<>(Category.class).getAll(typedText, 5));
                    return items;
                });

        category.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category object) {
                return object != null ? object.getId() + ". " + object.getName() : "";
            }

            @Override
            public Category fromString(String string) {
                var value = category.getItems().stream().filter(object ->
                        string.startsWith(Integer.toString(object.getId()))).findFirst().orElse(null);
                return value;
            }
        });

        ComboBoxAutocomplete.autoCompleteComboBox(manufacturer,
                (typedText) ->{
                    ObservableList<Manufacturer> items = FXCollections.observableArrayList();
                    items.addAll(new DefaultDbAccessor<>(Manufacturer.class).getAll(typedText, 5));
                    return items;
                });

        manufacturer.setConverter(new StringConverter<>() {

            @Override
            public String toString(Manufacturer object) {
                return object != null ? object.getId() + ". " + object.getName() : "";
            }

            @Override
            public Manufacturer fromString(String string) {
                var value = manufacturer.getItems().stream().filter(object ->
                        string.startsWith(Integer.toString(object.getId()))).findFirst().orElse(null);
                return value;
            }
        });

        FloatValidator.ApplyFor(price);
        IntegerValidator.ApplyFor(quantity);
    }

    /**
     * @see FormController#modelFromForm(Object)
     */
    @Override
    protected Product modelFromForm(Product item) {
        if(item == null) item = new Product();
        item.setName(name.getText());
        item.setDescription(description.getText());
        item.setPrice(Float.parseFloat(price.getText()));
        item.setQuantity(Integer.parseInt(quantity.getText()));
        item.setAvailable(available.isSelected());
        item.setCategory(ComboBoxAutocomplete.getComboBoxValue(category));
        item.setManufacturer(ComboBoxAutocomplete.getComboBoxValue(manufacturer));
        return item;
    }
    /**
     * @see FormController#formFromModel(Object)
     */
    @Override
    protected void formFromModel(Product item) {
        this.name.setText(item == null ? "" : item.getName());
        this.description.setText(item == null ? "" : item.getDescription());
        this.price.setText(item == null ? "" : String.valueOf(item.getPrice()));
        this.manufacturer.setValue(item == null ? null : manufacturers.stream().filter(m -> m.getId() == item.getManufacturer().getId()).findFirst().orElse(null));
        this.category.setValue(item == null ? null : categories.stream().filter(c -> c.getId() == item.getCategory().getId()).findFirst().orElse(null));
        this.quantity.setText(item == null ? "" : String.valueOf(item.getQuantity()));
        this.available.setSelected(item != null && item.isAvailable());
    }

    /**
     * @see FormController#validate()
     */
    @Override
    protected boolean validate(){

        return !name.getText().isEmpty() &&
                !description.getText().isEmpty() &&
                !price.getText().isEmpty() &&
                !quantity.getText().isEmpty() &&
                manufacturer.getValue() != null &&
                category.getValue() != null;
    }

    /**
     * Getters and setters.
     */
    @FXML
    public ManufacturerComboBoxCellFactory getManufacturerCellFactory() {
        return new ManufacturerComboBoxCellFactory();
    }

    @FXML
    public CategoryComboBoxCellFactory getCategoryCellFactory() {
        return new CategoryComboBoxCellFactory();
    }

    @FXML
    public ListCell<Manufacturer> getManufacturerButtonCell() {
        return new ManufacturerComboBoxCellFactory().call(null);
    }

    @FXML
    public ListCell<Category> getCategoryButtonCell() {
        return new CategoryComboBoxCellFactory().call(null);
    }

    @FXML
    public ObservableList<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    @FXML
    public ObservableList<Category> getCategories() {
        return categories;
    }

    @Override
    protected void setInfoLabel(boolean edit) {
        this.infoLabel.setText(edit ? "Edytuj produkt" : "Dodaj produkt");
    }

    @Override
    protected void showError(){
        this.errorLabel.setVisible(true);
    }

    @Override
    protected void hideError(){
        this.errorLabel.setVisible(false);
    }
}
