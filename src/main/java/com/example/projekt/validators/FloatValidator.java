package com.example.projekt.validators;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Helper Validator class.
 * Ensures that the input is a float.
 */
public class FloatValidator implements ChangeListener<String> {

    /**
     * TextField to be validated.
     */
    private final TextField textField;

    /**
     * Constructor.
     * @param node TextField to be validated.
     */
    private FloatValidator(TextField node){
        this.textField = node;
    }

    /**
     * Checks if new value is a float, if not, sets the old value.
     * @param observableValue Observable value.
     * @param oldValue Old value.
     * @param newValue New value.
     */
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if(newValue.equals("")) return;

        try{
            Float.parseFloat(newValue);
        }catch (NumberFormatException e){
            textField.setText(oldValue);
        }
    }

    /**
     * Creates a new FloatValidator and applies it to the given TextField.
     * @param node TextField to be validated.
     */
    public static void ApplyFor(TextField node){
        node.textProperty().addListener(new FloatValidator(node));
    }
}
