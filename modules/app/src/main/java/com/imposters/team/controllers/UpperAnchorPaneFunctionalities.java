package com.imposters.team.controllers;

import com.imposters.team.App;

import com.imposters.team.model.EnvChamber;
import com.imposters.team.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public abstract class UpperAnchorPaneFunctionalities {
    private double initialX;
    private double initialY;

    @FXML
    public Label statusChamber;
    @FXML
    private Label statusUser;
    @FXML
    public Label statusAdmin;

    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onMinimizingClicked(){
        App.getPrimaryStageOfProgram().setIconified(true);
    }

    public void setOnMousePressed(MouseEvent event){
        initialX = event.getSceneX();
        initialY = event.getSceneY();
    }

    @FXML
    public void clickWeiterBtn(){
        App.getPrimaryStageOfProgram().close();
    }

    public void setOnMouseDragged(MouseEvent event){
        App.getPrimaryStageOfProgram().setX(event.getScreenX() - initialX);
        App.getPrimaryStageOfProgram().setY(event.getScreenY() - initialY);
    }

    public void setStatusBar(User signedInUser){
        if(signedInUser.getPassword().equals("1")){
            statusAdmin.setText("Admin");
        }else{
            statusAdmin.setText("Local-user");
        }
        statusUser.setText(signedInUser.getFirstName() + " " + signedInUser.getLastName());
    }

    public void setStatusBar(User signedInUser, EnvChamber chamber){
        this.setStatusBar(signedInUser);
        statusChamber.setText(chamber.getIp());
    }
}