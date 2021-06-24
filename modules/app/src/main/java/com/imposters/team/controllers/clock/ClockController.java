package com.imposters.team.controllers.clock;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class ClockController {

    private final int SECOND = 1;
    private final int MINUTE = 1;
    private final SimpleIntegerProperty seconds = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty minutes = new SimpleIntegerProperty(0);
    private int countDownInMinutes;
    private final String message;

    public ClockController(int countDownInMinutes, String message) {
        this.countDownInMinutes = countDownInMinutes;
        this.message = message;
    }


    private void updateTime(Label clock, Label message) throws InterruptedException {
        while (this.minutes.get() < this.countDownInMinutes) {
            Platform.runLater(() ->
            {
                {
                    if (this.seconds.get() != 60) {
                        if (this.seconds.get() < 10) {
                            clock.setText(
                                    "00:0" +
                                            this.minutes.get() +
                                            ":0" + this.seconds.get()
                            );
                        } else {
                            clock.setText(
                                    "00:0" +
                                            this.minutes.get() +
                                            ":" + this.seconds.get()
                            );
                        }
                        this.seconds.set(this.seconds.get() + SECOND);
                    } else {
                        this.minutes.set(this.minutes.get() + MINUTE);
                        this.seconds.set(0);
                    }
                }
            });

            Thread.sleep(1000);
        }
        Platform.runLater(() ->
        {
            clock.setStyle("-fx-text-fill:green;");
            message.setText(this.message);
            message.setStyle("-fx-text-fill:green;");
        });
    }

    private void updateTime(Label clock) throws InterruptedException {
        while (this.minutes.get() < this.countDownInMinutes) {
            Platform.runLater(() ->
            {
                {
                    if (this.seconds.get() != 60) {
                        if (this.seconds.get() < 10) {
                            clock.setText(
                                    "00:0" +
                                            this.minutes.get() +
                                            ":0" + this.seconds.get()
                            );
                        } else {
                            clock.setText(
                                    "00:0" +
                                            this.minutes.get() +
                                            ":" + this.seconds.get()
                            );
                        }
                        this.seconds.set(this.seconds.get() + SECOND);
                    } else {
                        this.minutes.set(this.minutes.get() + MINUTE);
                        this.seconds.set(0);
                    }
                }
            });

            Thread.sleep(1000);
        }
        Platform.runLater(() ->
        {
            clock.setStyle("-fx-text-fill:green;");
        });
    }

    public void run(Label clock, Label message) {
        new Thread(() ->
        {
            try {
                this.updateTime(clock, message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void run(Label clock) {
        new Thread(() ->
        {
            try {
                this.updateTime(clock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String getCurrentTime() {
        return "00:0" + this.minutes.get() + ":" + this.seconds.get();
    }

    public void stopStopwatch() {
        this.countDownInMinutes = 0;
    }

}
