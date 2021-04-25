package com.imposters.team.controllers.loginViews;
import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private Label alertMessage;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    public void loginBtnClicked(ActionEvent event) {
        String password = passwordTextField.getText();
        String username = usernameTextField.getText();
        if(!(password.isEmpty() || username.isEmpty())){
            alertMessage.setText(
                    "logged in successfully, " +
                    Character.toUpperCase(username.charAt(0)) +
                    username.substring(1) + "!"
            );
        }else{
            passwordTextField.clear();
            usernameTextField.clear();
            alertMessage.setText("invalid username or password, try again!");
        }
    }

    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onMinimizingClicked(){
        App.getPrimaryStageOfProgram().toBack();
    }

}
