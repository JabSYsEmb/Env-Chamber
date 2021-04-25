package com.imposters.team.controllers.loginViews;
import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            App.getPrimaryStageOfProgram().close();
            this.toView();
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
    public void toView(){
        try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/loginViews/chamberSelect.fxml"));
        /* 
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
    }
    }
}
