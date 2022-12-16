package com.example.projekt.cellFactories;

import com.example.projekt.database.entities.Manufacturer;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Cell factory for the manufacturer combo box.
 * Takes the manufacturer object and displays its id and name.
 */
public class ManufacturerComboBoxCellFactory implements Callback<ListView<Manufacturer>, ListCell<Manufacturer>> {
    @Override
    public ListCell<Manufacturer> call(ListView<Manufacturer> l) {
        return new ListCell<>() {

            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
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
