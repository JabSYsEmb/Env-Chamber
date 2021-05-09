package com.imposters.team.controllers;

import com.imposters.team.App;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public abstract class CloseMinimizeFunctionalities{
    private double initialX;
    private double initialY;

    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onMinimizingClicked(){
        App.getPrimaryStageOfProgram().setIconified(true);
    }
    public void setOnMousePressed(MouseEvent mouseEvent){
        initialX = mouseEvent.getSceneX();
        initialY = mouseEvent.getSceneY();
        this.printMousePositionOut(0,0);
    }

    public void setOnMouseDragged(MouseEvent event){
        App.getPrimaryStageOfProgram().setX(event.getX() - initialX);
        App.getPrimaryStageOfProgram().setY(event.getY() - initialY);
        this.printMousePositionOut(event.getSceneX(),event.getSceneY());
    }

    public void printMousePositionOut(double x,double y){
        System.out.println("X: " + initialX + "\nX_1 : " + x + "\nX_1 - X :" + (x - initialX));
        System.out.println("Y: " + initialY + "\nY_1 : " + y + "\nY_1 - Y :" + (y - initialY));
    }

}