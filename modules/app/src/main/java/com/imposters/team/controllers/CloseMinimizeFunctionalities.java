package com.imposters.team.controllers;

import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class CloseMinimizeFunctionalities{
    public double yOffset = new Double(0);
    public double xOffset = new Double(0);
    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onMinimizingClicked(){
        App.getPrimaryStageOfProgram().setIconified(true);
    }

    public void setOnMousePressed(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void setOnMouseDragged(MouseEvent event){
        App.getPrimaryStageOfProgram().setX(event.getSceneX() - xOffset);
        App.getPrimaryStageOfProgram().setY(event.getSceneY() - yOffset);
    }
}