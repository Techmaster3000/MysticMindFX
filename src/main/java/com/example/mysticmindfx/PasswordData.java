package com.example.mysticmindfx;


import java.util.Objects;

public class PasswordData {
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;

    // Getters and Setters
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordData that = (PasswordData) o;
        return Objects.equals(oldPassword, that.oldPassword) &&
                Objects.equals(newPassword, that.newPassword) &&
                Objects.equals(repeatNewPassword, that.repeatNewPassword);
    }
    @Override
    public int hashCode() {
        return Objects.hash(oldPassword, newPassword, repeatNewPassword);
    }

    @Override
    public String toString() {
        return "PasswordData{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", repeatNewPassword='" + repeatNewPassword + '\'' +
                '}';
    }
}