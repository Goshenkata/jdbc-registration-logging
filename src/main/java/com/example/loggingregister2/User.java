package com.example.loggingregister2;

import javafx.scene.image.Image;

import java.time.LocalDate;
/**
This class maps to the user table:
 +-----------+-------------+------+-----+---------+----------------+
 | Field     | Type        | Null | Key | Default | Extra          |
 +-----------+-------------+------+-----+---------+----------------+
 | userID    | int         | NO   | PRI | NULL    | auto_increment |
 | username  | varchar(30) | NO   | UNI | NULL    |                |
 | firstname | varchar(30) | NO   |     | NULL    |                |
 | lastname  | varchar(30) | NO   |     | NULL    |                |
 | email     | varchar(30) | NO   |     | NULL    |                |
 | password  | varchar(30) | NO   |     | NULL    |                |
 | birthday  | date        | NO   |     | NULL    |                |
 | pfp       | mediumblob  | YES  |     | NULL    |                |
 +-----------+-------------+------+-----+---------+----------------+
 NOTE: if the profile picture is the default one it is stored as null so it doesn't needlessly take up space, mmm yes much efficency
 */
public class User {
    private final int userID;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDate birthDay;
    private final Image pfp;
    private final boolean exists;

    public User(int userID, String username, String firstName, String lastName, String email, LocalDate birthDay, Image pfp) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDay = birthDay;
        this.pfp = pfp;
        this.exists = true;
    }
    //this constructor is rep
    public User() {
        this.exists = false;
        this.userID = -1;
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.birthDay = null;
        this.pfp = null;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthDay=" + birthDay +

                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Image getPfp() {
        return pfp;
    }

    public boolean exists() {
        return exists;
    }
}
