package com.example.mysticmindfx;

public class LanguageHandler {
    private static Language language;
    private static Language currentLanguage = Language.ENGLISH;
    private static volatile LanguageHandler instance = null;

    private LanguageHandler() {
    }

    public static LanguageHandler getInstance() {
        if (instance == null) {
            synchronized (LanguageHandler.class) {

                if (instance == null) {

                    instance = new LanguageHandler();

                }
            }
        }
        return instance;
    }

    public static Language getLanguage() {
        return currentLanguage;
    }

    public void setLanguage(Language language) {
        currentLanguage = language;
    }

    public static String getLocalizedMessage(Message message) {
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
