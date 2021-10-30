package com.example.loggingregister2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
/**
This is the controller of the LoggedInScene, which is displayed AFTER a user has logged in successfully
*/
public class LoggedInController {

    @FXML
    private Label usernameTextField;
    @FXML
    private Label emailTextField;
    @FXML
    private Label firstNameTextField;
    @FXML
    private Label lastNameTextField;
    @FXML
    private Label birthdayTextField;
    @FXML
    private ImageView pfp;

    //sets the initial fields
    public void welcome(User user) {
        usernameTextField.setText(user.getUsername());
        emailTextField.setText(user.getEmail());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        birthdayTextField.setText(user.getBirthDay().toString());
        pfp.setImage(user.getPfp());
    }

}
