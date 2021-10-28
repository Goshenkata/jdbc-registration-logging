package com.example.loggingregister2;

import javafx.fxml.Initializable;

import javax.sql.RowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Database {

    public void register(String username, String firstname,
                         String lastname, String email, String password,
                         Date birthday, File pfp) throws SQLException, IOException {

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signuptest","myuser", "Grabutanter@123");
            Statement stmt = conn.createStatement();
        ) {
            String register = "INSERT INTO user VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(register);
            pstm.setNull(1,Types.NULL);
            pstm.setString(2,username);
            pstm.setString(3,firstname);
            pstm.setString(4,lastname);
            pstm.setString(5,email);
            pstm.setString(6,password);
            pstm.setDate(7,birthday);
            FileInputStream fin = new FileInputStream(pfp);
            pstm.setBinaryStream(8,fin);
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username) {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signuptest","myuser", "Grabutanter@123");
            Statement stmt = conn.createStatement();
        ) {
            PreparedStatement ps = conn.prepareStatement("SELECT userId FROM user WHERE username = ?");
            ps.setString(1,username);
            ResultSet resultSet = ps.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
