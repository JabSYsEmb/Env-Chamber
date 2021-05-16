package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;
import com.imposters.team.controllers.clock.ClockController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class BurnInTester5Controller extends UpperAnchorPaneFunctionalities implements Initializable{

    @FXML
    private Label BerichtID;
    @FXML
    private Label Datum;
    @FXML
    private Label ChamberID;
    @FXML
    private Label Arbeitername;
    @FXML
    private Label Arbeitersvorname;
    @FXML
    private Label ArbeiterID;
    @FXML
    private TableView table;


    @Override
    @FXML
    public void clickWeiterBtn() {
        ObservableList<String> courseData = FXCollections.observableArrayList(
                new String("Inheritance"),
                new String("Abstraction"),
                new String("Polymorphism"),
                new String("Generics"),
                new String("OOPS"),
                new String("Functions"));
        table.setItems(courseData);
        BerichtID.setText("1");
        Datum.setText("10.01.1999");
        ChamberID.setText("1");
        Arbeitername.setText("Stark");
        Arbeitersvorname.setText("Reiner");
        ArbeiterID.setText("66666");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}