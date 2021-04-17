package com.imposters.team.controllers.loginViews;
import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

import java.security.Guard;


public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private Label closeBtn;

    @FXML
    private Label textField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private CheckBox manuelInputCheckBox;

    @FXML
    public void loginBtnClicked(ActionEvent event) {
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

    @FXML
    public void onMouseEntered(){
        App.setCursor(Cursor.HAND);
        loginBtn.setStyle(
//                "-fx-border-color: #9F1010;" +
//                "-fx-text-fill: #9F1010;" +
                "-fx-background-color: #DEDEDE;" +
                "-fx-fill: true");
    }

    @FXML
    public void onMouseExited(){
        App.setCursor(Cursor.DEFAULT);
        loginBtn.setStyle("-fx-border-color: #054671;-fx-text-fill: #054671;-fx-background-color: #ffffff");
    }

    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfprogram().close();
    }

    @FXML
    public void onCloseEntered(){
        App.setCursor(Cursor.HAND);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.2, 0.5, 0.5));
        closeBtn.setEffect(dropShadow);
        closeBtn.setStyle("-fx-text-fill: #c51300");
    }

    @FXML
    public void onCloseExited(){
        App.setCursor(Cursor.DEFAULT);
        closeBtn.setEffect(null);
        closeBtn.setStyle("-fx-text-fill: #2f2f2f");
    }
}
