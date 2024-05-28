package com.example.mysticmindfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene login = new Scene(loginLoader.load());
        //set logo
        stage.getIcons().add(new javafx.scene.image.Image(HelloApplication.class.getResource("logo.png").toString()));
        stage.setTitle("Hello!");
        stage.setScene(login);
        stage.show();
        SceneSwitcher.getInstance().setMainStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}