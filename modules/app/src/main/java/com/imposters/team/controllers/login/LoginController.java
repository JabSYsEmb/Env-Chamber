package com.imposters.team.controllers.login;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.User;
import com.imposters.team.model.dao.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;

import java.util.ResourceBundle;

public class LoginController extends UpperAnchorPaneFunctionalities implements Initializable {

    @FXML
    private Label alertMessage;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    public void nextClicked() {
        this.infoChecker();
    }

    public void infoChecker(){
        try {
            String password = passwordTextField.getText();
            String username = usernameTextField.getText();
            User user = UserDao.getUserFromDatabase(username,this.db);
            if(user.getPassword().equals(this.db.passwordEncrypter(password))){
                Context.setUser(user);
                App.changeView("/fxml/login/chamberSelect.fxml");
            }else{
                textFieldCleaner();
            }
        }catch (Exception ex){
            textFieldCleaner();
        }
    }

    public void textFieldCleaner(){
        passwordTextField.clear();
        usernameTextField.clear();
        alertMessage.setText("invalid username or password, try again!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inject db in the Class beside initialization
        this.setDatabase();
    }
}

