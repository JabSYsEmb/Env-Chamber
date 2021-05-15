package com.imposters.team.controllers.clock;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class ClockController {

    private SimpleIntegerProperty seconds = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty minutes = new SimpleIntegerProperty(0);
    private String message;
    private int countDownInMinutes;

    private final int SECOND = 1;
    private final int MINUTE = 1;

    public ClockController(int countDownInMinutes, String message){
        this.countDownInMinutes = countDownInMinutes;
        this.message = message;
    }


    public void updateTime(Label clock, Label message) throws InterruptedException {

        while(this.minutes.get()!=this.countDownInMinutes) {
            Platform.runLater(
                    () -> {
                        {
                            if(this.seconds.get()!=60){
                                if(this.seconds.get()<10){
                                    clock.setText(
                                            "00:0"+
                                                    String.valueOf(this.minutes.get())+
                                                    ":0"+String.valueOf(this.seconds.get())
                                    );
                                }else{
                                    clock.setText(
                                            "00:0"+
                                                    String.valueOf(this.minutes.get())+
                                                    ":"+String.valueOf(this.seconds.get())
                                    );
                                }
                                this.seconds.set(this.seconds.get() + SECOND);
                            }else{
                                this.minutes.set(this.minutes.get() + MINUTE);
                                this.seconds.set(0);
                            }
                            if(this.minutes.get()==4 && this.seconds.get()==59){
                                clock.setStyle("-fx-text-fill:green;");
                                message.setText(this.message);
                                message.setStyle("-fx-text-fill:green;");
                            }
                        }
                    }
            );
            Thread.sleep(10);
        }
    }

    public void run(Label clock, Label message){
        new Thread(() -> {
            try {
                this.updateTime(clock,message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
