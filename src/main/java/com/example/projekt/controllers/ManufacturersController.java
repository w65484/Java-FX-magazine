package com.example.projekt.controllers;

import com.example.projekt.database.entities.Manufacturer;

/**
 * Controller class for manufacturers list.
 */
public class ManufacturersController extends ListController<Manufacturer>{

    public ManufacturersController() {
        super(Manufacturer.class);
    }
}
