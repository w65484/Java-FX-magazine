package com.example.projekt.controllers;

import com.example.projekt.database.entities.Category;

/**
 * Controller class for categories list.
 */
public class CategoriesController extends ListController<Category>{

    public CategoriesController() {
        super(Category.class);
    }
}
