package com.example.projekt.helpers;

import com.example.projekt.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Helper class for creating views from FXML files.
 */
public class ViewCreator {
    /**
     * Creates a view from a FXML file.
     *
     * @param viewName the FXML file name without the extension
     * @return view created from FXML file
     */
    public static Node CreateView(String viewName) throws IOException {
        // return products view
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewName + ".fxml"));
        return fxmlLoader.load();
    }
}
