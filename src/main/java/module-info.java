module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;

    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.logging;
    requires net.bytebuddy;
    requires java.sql;
    requires java.xml.bind;

    opens com.example.projekt to javafx.fxml;
    exports com.example.projekt;
    exports com.example.projekt.controllers;
    opens com.example.projekt.controllers to javafx.fxml;
    exports com.example.projekt.database.entities;
    opens com.example.projekt.database.entities to javafx.fxml, org.hibernate.orm.core;
    exports com.example.projekt.cellFactories;
    opens com.example.projekt.cellFactories to javafx.fxml, org.hibernate.orm.core;
    exports com.example.projekt.validators;
    opens com.example.projekt.validators to javafx.fxml, org.hibernate.orm.core;
    exports com.example.projekt.interfaces;
    opens com.example.projekt.interfaces to javafx.fxml, org.hibernate.orm.core;
    exports com.example.projekt.database;
    opens com.example.projekt.database to javafx.fxml, org.hibernate.orm.core;
    exports com.example.projekt.helpers;
    opens com.example.projekt.helpers to javafx.fxml, org.hibernate.orm.core;
}