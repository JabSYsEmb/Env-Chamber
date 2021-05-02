package com.imposters.team.controllers.loginViews;

import com.imposters.team.App;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.controllers.closeMinimizeFunctionalities;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends closeMinimizeFunctionalities {
    
    @FXML
    private Label alertMessage;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    public void loginBtnClicked() {
        String password = passwordTextField.getText();
        String username = usernameTextField.getText();
        MyJDBC db = App.getDatabase();
        if(!(password.isEmpty() || username.isEmpty())){
            alertMessage.setText(
                    "logged in successfully, " +
                    Character.toUpperCase(username.charAt(0)) +
                    username.substring(1) + "!"
            );
            App.changeView("/fxml/loginViews/chamberSelect.fxml");
        }else{
            passwordTextField.clear();
            usernameTextField.clear();
            alertMessage.setText("invalid username or password, try again!");
        }
    }

}
