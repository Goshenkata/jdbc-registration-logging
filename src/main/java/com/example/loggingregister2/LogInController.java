package com.example.loggingregister2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
/**
controller for the login screen
*/
public class LogInController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void GoToSignIn(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterScene.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.show();
    }


    public void login(ActionEvent event) throws IOException {
        User user = Database.login(username.getText(),password.getText());
        if (user.exists()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoggedInScene.fxml"));
            root = loader.load();
            LoggedInController loggedInController = loader.getController();
            loggedInController.welcome(user);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sign In");
            stage.show();
            System.out.println("succesfully logged in");
        } else {
            issueAlert("login failed! Please check your credentials again");
        }
    }

    private void issueAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
        alert.showAndWait();
    }

    public void setUsername(String usernameText) {
        username.setText(usernameText);
    }

    public void setPassword(String passwordText) {
        password.setText(passwordText);
    }
}