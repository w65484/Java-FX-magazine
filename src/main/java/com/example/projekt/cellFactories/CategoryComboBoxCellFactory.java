package com.example.projekt.cellFactories;

import com.example.projekt.database.entities.Category;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
/**
 * Cell factory for the category combo box.
 * Takes the category object and displays its id and name.
 */
public class CategoryComboBoxCellFactory implements Callback<ListView<Category>, ListCell<Category>> {
    @Override
    public ListCell<Category> call(ListView<Category> l) {
        return new ListCell<>() {

            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getId() + ".    " + item.getName());
                }
            }
        } ;
    }
}
