package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Thread.sleep;

public class BurnInTester2Controller extends UpperAnchorPaneFunctionalities {

    @FXML
    private Label clock;

    private ClockController clockController = new ClockController();
    @FXML
    public void weiterBtnClicked(){
        clock.textProperty().bindBidirectional(clockController.getHourMinuteSecond());
        new Thread (()->clockController.run()).start();
    }


}


class ClockController{
    private SimpleIntegerProperty second;
    private SimpleIntegerProperty minute;
    private SimpleIntegerProperty hour;
    private SimpleStringProperty time;

    public ClockController(){
        this.second = new SimpleIntegerProperty(0);
        this.minute = new SimpleIntegerProperty(0);
        this.hour   = new SimpleIntegerProperty(0);
        this.time   = new SimpleStringProperty("tttttttttttttt");
    }

    public void run() {
        for (;minute.equals(new SimpleIntegerProperty(2));) {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            Calendar cal = Calendar.getInstance();

            second = new SimpleIntegerProperty(cal.get(Calendar.SECOND));
            minute = new SimpleIntegerProperty(cal.get(Calendar.MINUTE));
            hour = new SimpleIntegerProperty(cal.get(Calendar.HOUR));
            //System.out.println(hour + ":" + (minute) + ":" + second);
            time = new SimpleStringProperty(hour + ":" + (minute) + ":" + second);
            System.out.println(time);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                //...
            }
        }
    }

//    public List<SimpleIntegerProperty> getHourMinuteSecond(){
//        return Arrays.asList(this.hour,this.minute,this.second);
//    }

    public SimpleStringProperty getHourMinuteSecond(){
        return this.time;
    }

//
//    public void updateClockSec(){
//        Thread clock = new Thread() {
//            public void run() {
//                for (;;) {
//                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
//                    Calendar cal = Calendar.getInstance();
//
//                    second = new SimpleIntegerProperty(cal.get(Calendar.SECOND));
//                    minute = new SimpleIntegerProperty(cal.get(Calendar.MINUTE));
//                    hour = new SimpleIntegerProperty(cal.get(Calendar.HOUR));
//                    //System.out.println(hour + ":" + (minute) + ":" + second);
//                    time = new SimpleStringProperty(hour + ":" + (minute) + ":" + second);
//
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException ex) {
//                        //...
//                    }
//                }
//            }
//        };
//        clock.start();
//    }
}
