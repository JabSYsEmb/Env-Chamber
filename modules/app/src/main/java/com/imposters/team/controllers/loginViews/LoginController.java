package com.imposters.team.controllers.loginViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private Label textField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private CheckBox manuelInputCheckBox;

    @FXML
    public void loginBtnClicked(ActionEvent event){
        String password = passwordTextField.getText();
        String username = usernameTextField.getText();
        if(!(password.isEmpty() || username.isEmpty())){
            textField.setText(
                    "logged in successfully, " +
                    Character.toUpperCase(username.charAt(0)) +
                    username.substring(1) + "!"
            );
        }else{
            passwordTextField.clear();
            usernameTextField.clear();
            textField.setText("invalid username or password, try again!");
        }
    }

}
