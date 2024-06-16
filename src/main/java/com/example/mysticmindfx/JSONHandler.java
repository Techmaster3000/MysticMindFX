package com.example.mysticmindfx;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONHandler {
    private String emailFieldName = "email";
    private String usernameFieldName = "username";
    private String passwordFieldName = "password";
    private ArrayList<User> userList = new ArrayList<>();
    private static JSONHandler instance = null;

    private JSONHandler() {
        readJSON();
    }

    public static JSONHandler getInstance() {
        if (instance == null) {
            instance = new JSONHandler();
        }
        return instance;
    }

    public void readJSON() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/com/example/mysticmindfx/userdata.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("users");
            for (Object user : users) {
                JSONObject userObject = (JSONObject) user;
                String email = (String) userObject.get(emailFieldName);
                String username = (String) userObject.get(usernameFieldName);
                String password = (String) userObject.get(passwordFieldName);
                userList.add(new User(username, password, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUser(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void addJson(String email, String username, String password) {
        JSONArray users = new JSONArray();
        JSONObject newuser = createUserJson(email, username, password);
        users.add(newuser);
        loadUsers(users);
        JSONObject root = new JSONObject();
        root.put("users", users);
        writeToJsonFile(root);
    }

    private JSONObject createUserJson(String email, String username, String password) {
        JSONObject newuser = new JSONObject();
        newuser.put(emailFieldName, email);
        newuser.put(usernameFieldName, username);
        newuser.put(passwordFieldName, password);
        return newuser;
    }

    private void writeToJsonFile(JSONObject root) {
        try (FileWriter file = new FileWriter("src/main/resources/com/example/mysticmindfx/userdata.json")) {
            file.write(root.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            readJSON();
        }
    }

    private JSONArray loadUsers(JSONArray users) {
        for (User user : userList) {
            JSONObject userJson = createUserJson(user.getEmail(), user.getUsername(), user.getPassword());
            users.add(userJson);
        }
        return users;
    }

    public void updateUser(String oldEmail, String newEmail, String newUsername, String newPassword) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(oldEmail)) {
                String encryptedPassword = DigestUtils.sha256Hex(newPassword);
                userList.set(i, new User(newUsername, encryptedPassword, newEmail));
                writeJSON();
                return;
            }
        }
    }

    private void writeJSON() {
        JSONArray users = new JSONArray();
        loadUsers(users);
        JSONObject root = new JSONObject();
        root.put("users", users);
        writeToJsonFile(root);
    }
    public void saveUser(User user){
        userList.add(user);
        writeJSON();
    }
}

