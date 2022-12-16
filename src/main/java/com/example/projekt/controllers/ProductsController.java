package com.example.projekt.controllers;

import com.example.projekt.database.DefaultDbAccessor;
import com.example.projekt.database.entities.Category;
import com.example.projekt.database.entities.Manufacturer;
import com.example.projekt.database.entities.Product;
import com.example.projekt.validators.FloatValidator;
import com.example.projekt.validators.IntegerValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for products list.
 */
public class ProductsController extends ListController<Product> {

    public ProductsController() {
        super(Product.class);
    }
}
