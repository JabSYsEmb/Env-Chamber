package com.imposters.team.controllers.clock;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class ClockController {

    private SimpleIntegerProperty seconds = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty minutes = new SimpleIntegerProperty(0);
<<<<<<< HEAD
    private int countDownInMinutes;
    private String message;
=======
    private String message;
    private int countDownInMinutes;
>>>>>>> a76ed9624cf4d2dd29584211dd4ac7e3d1e46698

    private final int SECOND = 1;
    private final int MINUTE = 1;

    public ClockController(int countDownInMinutes, String message){
        this.countDownInMinutes = countDownInMinutes;
        this.message = message;
    }


<<<<<<< HEAD
    private void updateTime(Label clock, Label message) throws InterruptedException {
=======
    public void updateTime(Label clock, Label message) throws InterruptedException {
>>>>>>> a76ed9624cf4d2dd29584211dd4ac7e3d1e46698

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
<<<<<<< HEAD
=======
                            if(this.minutes.get()==4 && this.seconds.get()==59){
                                clock.setStyle("-fx-text-fill:green;");
                                message.setText(this.message);
                                message.setStyle("-fx-text-fill:green;");
                            }
>>>>>>> a76ed9624cf4d2dd29584211dd4ac7e3d1e46698
                        }
                    }
            );
            Thread.sleep(10);
        }
<<<<<<< HEAD
        Platform.runLater(()->{
            clock.setStyle("-fx-text-fill:green;");
            message.setText(this.message);
            message.setStyle("-fx-text-fill:green;");
        });
=======
>>>>>>> a76ed9624cf4d2dd29584211dd4ac7e3d1e46698
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
<<<<<<< HEAD

    public String getCurrentTime(){
        return "00:0"+String.valueOf(this.minutes.get())+":"+String.valueOf(this.seconds.get());
    }

    public void stopStopwatch(){
        this.countDownInMinutes = 0;
    }

=======
>>>>>>> a76ed9624cf4d2dd29584211dd4ac7e3d1e46698
}
