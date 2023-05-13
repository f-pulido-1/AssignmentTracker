package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 29, 2023
 * Description: Represents a user of the app.
 */

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {
    // Fields
    @PrimaryKey(autoGenerate = true)
    private int userId; // Primary key of the table

    private String username;
    private String password;
    private boolean isAdmin;
    private String firstName;
    private String lastName;

    // Constructor(s)
    public User(String firstName, String lastName, String username, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters & Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Other
    @Override
    public String toString() {
        return "User#" + userId + ": " + username + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nisAdmin: " + isAdmin + "\n\n";
    }
}