//module com.example.mysticmindfx {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires javafx.web;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;
//    requires com.fasterxml.jackson.databind;
//    requires json.simple;
//    requires com.google.gson;
//    requires jbcrypt;
//
//    opens com.example.mysticmindfx to javafx.fxml;
//    exports com.example.mysticmindfx;
//}

module com.example.aiassistent {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jbcrypt;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires json.simple;
    requires org.apache.commons.codec;
    requires org.json;
    requires java.desktop;

    requires com.fasterxml.jackson.databind;
    requires annotations;

    opens com.example.mysticmindfx to javafx.fxml, com.google.gson;
    exports com.example.mysticmindfx;
}