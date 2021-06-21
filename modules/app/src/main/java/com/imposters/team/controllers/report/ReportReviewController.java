package com.imposters.team.controllers.report;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.controllers.context.Context;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.net.URL;


public class ReportReviewController extends MainConfigurations implements Initializable {

    @FXML
    private Label berichtID;
    @FXML
    private Label datum;
    @FXML
    private Label chamberID;
    @FXML
    private Label arbeitername;
    @FXML
    private Label arbeitersvorname;
    @FXML
    private Label arbeiterID;
    @FXML
    private TableView table;


    @Override
    @FXML
    public void nextClicked() {
        ObservableList<String> courseData = FXCollections.observableArrayList(
                "Inheritance",
                "Abstraction",
                "Polymorphism",
                "Generics",
                "OOPS",
                "Functions");
        table.setItems(courseData);
        berichtID.setText("1");
        datum.setText("10.01.1999");
        chamberID.setText("1");
        arbeitername.setText("Stark");
        arbeitersvorname.setText("Reiner");
        arbeiterID.setText("66666");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
    }
}