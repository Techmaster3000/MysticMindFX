package com.example.mysticmindfx;

import com.example.mysticmindfx.Controllers.HelloController;
import com.example.mysticmindfx.Controllers.IController;
import com.example.mysticmindfx.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    // Singleton pattern
    private static SceneSwitcher instance = null;
    private static Stage mainStage;
    private static String user = null;
    private Language currentLanguage = Language.ENGLISH;
    private User currentUser;
    private Language language;

    private SceneSwitcher() {}

    public static SceneSwitcher getInstance() {
        if (instance == null) {
            instance = new SceneSwitcher();
        }
        return instance;
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }

    public void setLanguage(Language language) {
        this.currentLanguage = language;
    }

    public Language getLanguage() {
        return currentLanguage;
    }

    public void switchScene(String sceneName, String windowTitle, String email) {
        try {
            String sceneFile = sceneName;
            if (currentLanguage == Language.DUTCH) {
                sceneFile = sceneFile.replace(".fxml", "-NL.fxml");
            }

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(sceneFile));
            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);

            //Initialize the controller of the new scene regardless of classtype
            IController controller = loader.getController();


            //check if the controller is an instance of MainController
            if (controller instanceof MainController && email != null) {
                ((MainController) controller).setUser(user);
                if (user != null) {
                    ((MainController) controller).setUser(user);
                }
                else {
                //((MainController) controller).setUser(user);
                }
            }

            // Check if the controller is an instance of ChangeAccountInfoController
            if (controller instanceof ChangeAccountInfoController) {
                User user = JSONHandler.getInstance().findUser(email);
                ((ChangeAccountInfoController) controller).setCurrentUser(user);
            }

            // Initialize the controller
            controller.initialize(null, null);

            mainStage.setTitle(windowTitle);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void removeUser() {
        user = null;
    }

    public String getLocalizedMessage(Message message) {
        if (getLanguage() == Language.DUTCH) {
            switch (message) {
                case SIGN_IN_LINK:
                    return "Inloglink aangeklikt";
                case SIGN_UP_LINK:
                    return "Aanmeldlink aangeklikt";
                case FILL_ALL_FIELDS:
                    return "Vul alle velden in.";
                case EMAIL_EXISTS:
                    return "Email bestaat al.";
                case PASSWORD_REQS:
                    return "Wachtwoord moet minimaal 4 tekens en 1 cijfer bevatten.";
                case EMAILS_DO_NOT_MATCH:
                    return "Emails komen niet overeen.";
                case PASSWORDS_DO_NOT_MATCH:
                    return "Wachtwoorden komen niet overeen.";
                case SIGN_UP_SUCCESS_TITLE:
                    return "Succesvolle aanmelding";
                case SIGN_UP_SUCCESS_HEADER:
                    return "Welkom ";
                case SIGN_UP_SUCCESS_CONTENT:
                    return "Je bent succesvol aangemeld!";
                case EMAIL_NOT_FOUND:
                    return "Email niet gevonden";
                case INCORRECT_PASSWORD:
                    return "Onjuist wachtwoord";
                case LOGIN_SUCCESS_TITLE:
                    return "Succesvolle login";
                case LOGIN_SUCCESS_HEADER:
                    return "Welkom ";
                case LOGIN_SUCCESS_CONTENT:
                    return "Je bent succesvol ingelogd!";
                case DELETE_CHAT_CONFIRMATION:
                    return "Weet je zeker dat je deze chat wilt verwijderen?";
                case RENAME_CHAT:
                    return "Chat hernoemen";
                case REMOVE_CHAT:
                    return "Weet je zeker dat je deze chat wilt verwijderen?";
                case REMOVE_CHAT_TITLE:
                    return "Verwijder Chat";
                default:
                    return "";
            }
        } else {
            switch (message) {
                case SIGN_IN_LINK:
                    return "Sign In Link Clicked";
                case SIGN_UP_LINK:
                    return "Sign Up Link Clicked";
                case FILL_ALL_FIELDS:
                    return "Please fill in all fields.";
                case EMAIL_EXISTS:
                    return "Email already exists.";
                case PASSWORD_REQS:
                    return "Password must contain at least 4 characters and 1 number.";
                case EMAILS_DO_NOT_MATCH:
                    return "Emails do not match.";
                case PASSWORDS_DO_NOT_MATCH:
                    return "Passwords do not match.";
                case SIGN_UP_SUCCESS_TITLE:
                    return "Sign Up Successful";
                case SIGN_UP_SUCCESS_HEADER:
                    return "Welcome ";
                case SIGN_UP_SUCCESS_CONTENT:
                    return "You have successfully signed up!";
                case EMAIL_NOT_FOUND:
                    return "Email not found";
                case INCORRECT_PASSWORD:
                    return "Incorrect Password";
                case LOGIN_SUCCESS_TITLE:
                    return "Login Successful";
                case LOGIN_SUCCESS_HEADER:
                    return "Welcome ";
                case LOGIN_SUCCESS_CONTENT:
                    return "You have successfully logged in!";
                case DELETE_CHAT_CONFIRMATION:
                    return "Are you sure you want to delete this chat?";
                case RENAME_CHAT:
                    return "Rename Chat";
                case REMOVE_CHAT:
                    return "Are you sure you want to remove this chat?";
                case REMOVE_CHAT_TITLE:
                    return "Remove Chat";
                default:
                    return "";
            }
        }
    }
}
