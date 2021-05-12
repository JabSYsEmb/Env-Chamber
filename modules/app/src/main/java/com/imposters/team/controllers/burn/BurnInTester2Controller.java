package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.net.URL;


public class BurnInTester2Controller extends UpperAnchorPaneFunctionalities implements Initializable{

    private SimpleIntegerProperty seconds = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty minutes = new SimpleIntegerProperty(0);
    private final int SECOND = 1;
    private final int MINUTE = 1;

    @FXML
    private AnchorPane clockAnchor;
    @FXML
    private Label clock;

    public void updateTime(SimpleIntegerProperty timeInSec,SimpleIntegerProperty timeInMin) throws InterruptedException {

        while(timeInMin.get()!=5) {
            Platform.runLater(
                    () -> {
                        {
                            if(timeInSec.get()!=60){
                                if(timeInSec.get()<10){
                                    clock.setText(
                                            "00:0"+
                                            String.valueOf(timeInMin.get())+
                                            ":0"+String.valueOf(timeInSec.get())
                                    );
                                }else{
                                    clock.setText(
                                            "00:0"+
                                            String.valueOf(timeInMin.get())+
                                            ":"+String.valueOf(timeInSec.get())
                                    );
                                }
                                timeInSec.set(timeInSec.get() + SECOND);
                            }else{
                                timeInMin.set(timeInMin.get() + MINUTE);
                                timeInSec.set(0);
                            }
                            if(timeInMin.get()==4 && timeInSec.get()==59){
                                clockAnchor.setStyle("-fx-background-color:#993730;");
                            }
                        }
                    }
            );
            Thread.sleep(100);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> {
            try {
                updateTime(seconds,minutes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    @Override
    public void clickWeiterBtn(){
        App.changeView("/fxml/login/login.fxml");
    }
}