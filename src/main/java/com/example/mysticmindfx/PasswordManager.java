package com.example.mysticmindfx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;


public class PasswordManager {
    private static final String FILE_PATH = "wachtwoord.json";

    public static void savePasswordData(com.example.mysticmindfx.PasswordData passwordData) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Hash het wachtwoord voordat je het opslaat
            passwordData.setOldPassword(hashPassword(passwordData.getOldPassword()));

            gson.toJson(passwordData, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}

class PasswordManager2 {
    private static final String FILE_PATH = "wachtwoord.json";

    public static PasswordData loadPasswordData() {
        try (FileReader fileReader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            return gson.fromJson(fileReader, com.example.mysticmindfx.PasswordData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}