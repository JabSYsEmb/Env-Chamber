package com.imposters.team.controllers.login;

import com.imposters.team.controllers.UpperAndLowerBarConfigurator;
import com.imposters.team.client.Client;
import com.imposters.team.App;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.dao.UserDao;
import javafx.scene.control.TextField;
import com.imposters.team.model.User;
import com.imposters.team.db.MyJDBC;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;

import java.util.ResourceBundle;

public class HomePageController extends UpperAndLowerBarConfigurator implements Initializable
{
    @FXML
    private Label alertMessage;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    public void nextClicked() 
    {
        this.infoChecker();
    }

    public void infoChecker()
    {
        try 
        {
            String password = passwordTextField.getText();
            String username = usernameTextField.getText();
            User user = UserDao.getUserFromDatabase(username,this.db);
            if(user.getPassword().equals(this.db.passwordEncrypter(password)))
            {
                Context.setUser(user);
                App.changeView("/fxml/chamber-selection/ChamberSelection.fxml");
            }
            else
            {
                textFieldCleaner();
            }
        }
        catch (Exception ex)
        {
            textFieldCleaner();
        }
    }

    public void textFieldCleaner()
    {
        passwordTextField.clear();
        usernameTextField.clear();
        alertMessage.setText("invalid username or password, try again!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        // Inject db in the Class beside initialization
        this.db = new MyJDBC();

        // Initialize a thread for communication with the server
        new Thread(() -> this.client = new Client("127.0.0.1",2332))
                .start();
    }
}

