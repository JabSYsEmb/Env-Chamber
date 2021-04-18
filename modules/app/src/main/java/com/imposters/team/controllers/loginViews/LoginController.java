package com.imposters.team.controllers.loginViews;
import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


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
            loginBtn.setStyle(getBtnEnteredMode());
    }

    @FXML
    public void onMouseExited(){
        App.setCursor(Cursor.DEFAULT);
        loginBtn.setStyle(null);
    }

    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onCloseEntered(){
        App.setCursor(Cursor.HAND);
        closeBtn.setStyle(getCloseEnteredMode());
    }

    @FXML
    public void onCloseExited(){
        App.setCursor(Cursor.DEFAULT);
        closeBtn.setEffect(null);
        closeBtn.setStyle("-fx-text-fill: #2f2f2f");
    }

    public String getBtnEnteredMode(){
        return  "-fx-effect:             dropshadow(three-pass-box," +
                                        "#8195ad, 1, 0.5, 3, 2);" +
                "-fx-text-fill:          #ffffff;" +
                "-fx-border-color:       #0f4ff1;" +
                "-fx-background-color:   #0d40c1;";
    }
    public String getCloseEnteredMode(){
        return  "-fx-effect:            dropshadow(one-pass-box," +
                                        "#828187, 2, 0.6, 1.4, 1.4);"+
                "-fx-text-fill:         #c51300;";
    }

}
