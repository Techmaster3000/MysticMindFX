package com.example.mysticmindfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.*;
import javax.xml.catalog.CatalogFeatures;

import java.io.IOException;
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneSwitcher sceneSwitcher = SceneSwitcher.getInstance();
        sceneSwitcher.setMainStage(stage);
        sceneSwitcher.setLanguage(Language.ENGLISH); // Set the default language

        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene login = new Scene(loginLoader.load());
        stage.getIcons().add(new javafx.scene.image.Image(HelloApplication.class.getResource("Images/logo.png").toString()));
        stage.setTitle("Login");
        stage.setScene(login);
        stage.show();
        stage.setResizable(true);
        stage.setMinWidth(640);
        stage.setMinHeight(400);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
