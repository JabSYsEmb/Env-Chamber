package com.imposters.team.controllers;

import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public abstract class UpperAnchorPaneFunctionalities {
    private double initialX,initialY;

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
    public void weiterBtnClicked(){

    }

    public void setOnMouseDragged(MouseEvent event){
        App.getPrimaryStageOfProgram().setX(event.getScreenX() - initialX);
        App.getPrimaryStageOfProgram().setY(event.getScreenY() - initialY);
    }
}