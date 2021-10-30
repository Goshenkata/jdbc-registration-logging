package com.example.loggingregister2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 controller for the register screen
 */

public class RegisterSceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private File pfp;

    @FXML
    private ImageView pfpImageView;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private DatePicker birthday;

    public void GoToLogIn(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LogINScene.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void register(ActionEvent event) throws SQLException, IOException {
        if (checkInput()) {
            Database.register(username.getText(),firstName.getText(),lastName.getText(),
                        email.getText(),password.getText(), Date.valueOf(birthday.getValue()), pfp);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogINScene.fxml"));
            root = loader.load();

            LogInController logInController = loader.getController();
            logInController.setUsername(username.getText());
            logInController.setPassword(password.getText());

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }
    }
//does a series of checks on the registration input
    private boolean checkInput() {
        return checkUsername(username.getText())
                && checkName(firstName.getText(),lastName.getText())
                && checkEmail(email.getText())
                && checkPassword(password.getText())
                && checkDate(birthday.getValue());

    }
/*
Checks if the date
1) not null
2) the user is over 18
3) birthday date is realistically possible
*/
private boolean checkDate(LocalDate birthday) {
        if (birthday == null) {
            issueAlert("Please enter your birthday");
            return false;
        }
        if (ChronoUnit.YEARS.between(birthday,LocalDate.now()) < 18) {
            issueAlert("You must be over 18 to use this service");
            return  false;
        }
        if (ChronoUnit.YEARS.between(birthday, LocalDate.now())>=120) {
            issueAlert("Pls enter a valid date");
            return  false;
        }
        return true;
    }
    /*
    Checks if the password
    1) is not null
    2) is over 8 characters
    3) can fit in varchar(30)
    */
    private boolean checkPassword(String password) {
        if (password.length()==0) {
            issueAlert("Please enter a password!");
            return false;
        }
        if (password.length()<8) {
            issueAlert("password must be atleast 8 characters long");
            return false;
        }
        if (password.length()>30) {
            issueAlert("password must be less than 30 characters;");
            return false;
        }
        return true;
    }

    /*
  Checks if the email
  1) can fit in varchar(30)
  2) not null
  3) does a regex to check it's actually an email

  */
    private boolean checkEmail(String email) {
        if (email.length()>30) {
            issueAlert("email to long :(");
            return false;
        }
        if (email.length()==0) {
            issueAlert("Please enter a email address");
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            issueAlert("enter a valid email!");
            return false;
        }
        return true;
    }

    /*
      checks if the first and last name
      1)can fit in varchar(30)
      2) is not null
      */
    private boolean checkName(String firstName, String lastName) {
        if (firstName.length()>30) {
            issueAlert("First name must be less that 30 characters");
            return false;
        }
        if (firstName.length()==0) {
            issueAlert("Please enter your first name");
            return false;
        }
        if (lastName.length()>30) {
            issueAlert("last name must be less that 30 characters");
            return false;
        }
        if (lastName.length()==0) {
            issueAlert("Please enter your last name");
            return false;
        }
        return true;
    }
/*
    checks if the first and last name
      1)can fit in varchar(30)
      2) is not null
      3)is not already taken
            */
    private boolean checkUsername(String username) {
        //check length
        if (username.length()>30) {
            issueAlert("The username needs to be less than 30 characters");
            return false;
        }
        if (username.length()==0) {
            issueAlert("Please enter your username");
            return false;
        }
        //check if unique
        if (Database.isUsernameTaken(username)) {
            issueAlert("Username is already taken");
            return false;
        }
            return true;
    }

    private void issueAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
        alert.showAndWait();
    }

    public void uploadPfp(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose pfp");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        pfp = fileChooser.showOpenDialog(stage);
        try {
            if ((pfp.length()/1024)/1024 <=10) {
                Image image = new Image(new BufferedInputStream(new FileInputStream(pfp)));
                pfpImageView.setImage(image);
            } else {
                issueAlert("The image needs to be less than 10MB");
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = null;
        try {
            image = new Image(new BufferedInputStream(new FileInputStream("src/main/resources/com/example/loggingregister2/defaultPfp.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pfpImageView.setImage(image);
    }
}
