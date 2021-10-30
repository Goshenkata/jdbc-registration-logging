package com.example.loggingregister2;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
/**
This class handles all database operations through static methods
*/
public class Database {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/signuptest";
    //TODO SET USER AND PASSWORD
    private static final String USER = "USER";
    private static final String PASS = "PASSWORD@123";

    /*
    Inputs the data into the user table
    Looking back it would have been a better design to do data validation here but oh well...
     */
    public static void register(String username, String firstname,
                                String lastname, String email, String password,
                                Date birthday, File pfp) throws SQLException, IOException {

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL, USER, PASS);
             Statement stmt = conn.createStatement()
        ) {
            String register = "INSERT INTO user VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(register);
            pstm.setNull(1, Types.NULL);
            pstm.setString(2, username);
            pstm.setString(3, firstname);
            pstm.setString(4, lastname);
            pstm.setString(5, email);
            pstm.setString(6, password);
            pstm.setDate(7, birthday);
            try {
                FileInputStream fin = new FileInputStream(pfp);
                pstm.setBinaryStream(8, fin);
            } catch (NullPointerException e) {
                pstm.setNull(8, Types.NULL);
            }
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //returns a User object given the username and password,
    //if it can't do so it returns a null User with ~exists~ boolean variable set to false
    //this is needlessly complicated :/
    //todo some nuclear level refactoring needed on this dogshit implementation
    public static User login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL, USER, PASS);
             Statement stmt = conn.createStatement()
        ) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int userID = resultSet.getInt("userId");
                String usernameOfUser = resultSet.getString("username");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String passwordOfUser = resultSet.getString("password");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                InputStream imageInputStream = resultSet.getBinaryStream("pfp");
                Image pfp;
                if (imageInputStream == null) {
                    pfp = new Image(new BufferedInputStream(new FileInputStream("src/main/resources/com/example/loggingregister2/defaultPfp.png")));
                } else {
                    pfp = new Image(imageInputStream);
                }
                return new User(userID, usernameOfUser, firstName, lastName, email, birthday, pfp);
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
        //
        return new User();
    }

    //returns true if the username is in the user table and false if it is available
    public static boolean isUsernameTaken(String username) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL, USER, PASS);
             Statement stmt = conn.createStatement()
        ) {
            PreparedStatement ps = conn.prepareStatement("SELECT userId FROM user WHERE username = ?");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                System.out.println(username + " already exists :(");
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
