package com.imposters.team.controllers.login;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends UpperAnchorPaneFunctionalities implements Initializable {

    private MyJDBC db;

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
        String username_data = db.executeStringQuery("SELECT Username from User where Username LIKE 'nino' ;");
        String password_data = db.executeStringQuery("SELECT Password from User where Username LIKE 'nino' ;");
//        if(new User(username_data,password_data,true).isAdministrator()){
//
//        }
        if((username.equals(username_data) && password_data.equals(db.passwordEncrypter(password)))){
            new Thread(() -> {
                App.getToServerSender().toServer(
                        new StringBuilder()
                                .append("STRT|CabinetControl1|")
                                .append(username)
                                .append("|Admin|10")
                                .toString());
            }).start();
            alertMessage.setText(
                    "logged in successfully, " +
                    Character.toUpperCase(username.charAt(0)) +
                    username.substring(1) + "!"
            );
            App.changeView("/fxml/login/chamberSelect.fxml");
        }else{
            passwordTextField.clear();
            usernameTextField.clear();
            alertMessage.setText("invalid username or password, try again!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(()->{this.db = App.getDatabase();}).start();
        new Thread(() -> {
            alertMessage.setText(MyJDBC.getDBConnectionStatus(this.db));
        }).start();
    }
}

