package com.imposters.team.controllers;

import com.imposters.team.App;
import javafx.fxml.FXML;

public class closeMinimizeFunctionalities{

    @FXML
    public void onCloseClicked(){
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onMinimizingClicked(){
        App.getPrimaryStageOfProgram().toBack();
    }
}
