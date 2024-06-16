module com.example.mysticmindfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires json.simple;
    requires org.json;
    requires annotations;
    requires org.apache.commons.codec;
    requires org.apache.commons.io;


    opens com.example.mysticmindfx to javafx.fxml;
    exports com.example.mysticmindfx;
    exports com.example.mysticmindfx.Controllers;
    opens com.example.mysticmindfx.Controllers to javafx.fxml;
    exports com.example.mysticmindfx.AIService;
    opens com.example.mysticmindfx.AIService to javafx.fxml;
}