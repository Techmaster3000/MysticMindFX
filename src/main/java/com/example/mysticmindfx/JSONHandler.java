package com.example.mysticmindfx;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONHandler {
    //read a JSON file
    static ArrayList<User> userList = new ArrayList<>();
    //singleton pattern
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
    public static void readJSON() {
        JSONParser parser = new JSONParser();
        //read a json file with e-mail, username and password
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/com/example/mysticmindfx/userdata.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("users");
            for (Object user : users) {
                JSONObject userObject = (JSONObject) user;
                String email = (String) userObject.get("email");
                String username = (String) userObject.get("username");
                String password = (String) userObject.get("password");
                userList.add(new User(username, password, email));

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public static User findUser(String email) {
        //find a user in the JSON file
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public static void addJson(String email, String username, String password) {
        JSONArray users = new JSONArray();
        JSONObject newuser = new JSONObject();
        newuser.put("email", email);
        newuser.put("username", username);
        newuser.put("password", password);
        users.add(newuser);
        for (User user : userList) {
            JSONObject user1 = new JSONObject();
            user1.put("email", user.getEmail());
            user1.put("username", user.getUsername());
            user1.put("password", user.getPassword());
            users.add(user1);
        }
        JSONObject root = new JSONObject();
        root.put("users", users);
        try (FileWriter file = new FileWriter("userdata.json")) {
            String content = root.toJSONString();
            file.write(content);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the JSON file.");
            e.printStackTrace();
        }
    }
}
