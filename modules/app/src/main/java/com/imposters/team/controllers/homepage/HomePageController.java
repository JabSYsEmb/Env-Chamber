package com.imposters.team.controllers.homepage;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.dao.UserDao;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import com.imposters.team.model.User;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HomePageController extends MainConfigurations implements Initializable {
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

    public void infoChecker() {
        try {
            String password = passwordTextField.getText();
            String username = usernameTextField.getText();
            User user = UserDao.getUserFromDatabase(username, this.db);
            if (user.getPassword().equals(this.db.passwordEncrypter(password))) {
                Context.setUser(user);
                App.changeView("/fxml/chamber-selection/ChamberSelection.fxml");
            } else {
                textFieldCleaner();
            }
        } catch (Exception ex) {
            textFieldCleaner();
        }
    }

    public void textFieldCleaner() {
        passwordTextField.clear();
        usernameTextField.clear();
        alertMessage.setText("invalid username or password, try again!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.checkDatabaseStatus();
    }

    public void checkDatabaseStatus() {
        try {
            if(!this.db.getConnection().isClosed()) {
                this.alertMessage.setText("Prüfen Sie Ihre Datenbankverbindung");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

