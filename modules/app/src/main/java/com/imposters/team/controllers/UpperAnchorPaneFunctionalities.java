package com.imposters.team.controllers;

import com.imposters.team.App;

import com.imposters.team.client.Communicator;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.EnvChamber;
import com.imposters.team.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class UpperAnchorPaneFunctionalities {
    private double initialX;
    private double initialY;

    protected Communicator client = App.getToServerSender();
    protected MyJDBC db;

    @FXML
    protected Label statusChamber;
    @FXML
    protected Label statusUser;
    @FXML
    protected Label statusAdmin;

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
        if(signedInUser.isAdminStatus()){
            statusAdmin.setText("Admin");
        }else{
            statusAdmin.setText("Limited User");
        }
        statusUser.setText(signedInUser.getFirstName() + " " + signedInUser.getLastName());
    }

    public void setStatusBar(User signedInUser, EnvChamber chamber){
        this.setStatusBar(signedInUser);
        statusChamber.setText(chamber.getIp());
    }

    public void setDatabase(){
        this.db = App.getDatabase();
    }

    public void onKeyPressedListener(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case ENTER: nextClicked();
        }
    }

    protected abstract void nextClicked();

}