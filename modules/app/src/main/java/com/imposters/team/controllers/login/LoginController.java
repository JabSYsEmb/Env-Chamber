package com.imposters.team.controllers.login;

import com.imposters.team.App;
import com.imposters.team.client.Sender;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.User;
import com.imposters.team.model.dao.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;

import java.util.ResourceBundle;

public class LoginController extends UpperAnchorPaneFunctionalities implements Initializable {

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
        User user = UserDao.getUserFromDatabase(username,this.db);
        System.out.println(user.toString());
        if(user.getPassword().equals(this.db.passwordEncrypter(password))){
            Sender.setSTRMessageForCabinetMock(user, Arrays.asList("STR","Test"));

            alertMessage.setText(
                    "logged in successfully, " +
                    Character.toUpperCase(user.getUsername().charAt(0)) +
                    username.substring(1) + "!"
            );
            Context.setUser(user);
            App.changeView("/fxml/login/chamberSelect.fxml");
        }else{
            passwordTextField.clear();
            usernameTextField.clear();
            alertMessage.setText("invalid username or password, try again!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inject db in the Class beside initialization
        this.setDatabase();
    }
}

