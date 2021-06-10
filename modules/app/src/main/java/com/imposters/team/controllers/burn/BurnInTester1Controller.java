package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.client.Communicator;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class BurnInTester1Controller extends UpperAnchorPaneFunctionalities
        implements Initializable {
    private int NUMBER_OF_UNITS  = 20;

    private List<List<String>> units = new ArrayList<>();
    private String message;

    @FXML
    private Label alertMessageInit;

    @FXML
    private TextField slotNumber;

    @FXML
    private TextField orderNumber;

    @FXML
    @Override
    public void nextClicked() {

        this.units.stream().forEach(
                item -> this.client.toServer(
                        item.stream().collect(Collectors.joining()))
        );

        // End of Initialization
        this.client.toServer("ENDINIT");

        App.changeView("/fxml/burnIn-views/burnInTester2.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setStatusBar(Context.getUser(),Context.getEnvChamber());
    }

    @FXML
    public void addNewUnit(){
        this.message = "";
        String colorOfMsg = "red";
        if(this.slotNumber.getText().isEmpty() || this.orderNumber.getText().isEmpty())
        {
            this.message = "Ungültige Gerät, jedes Gerät sollte eine Artikelnummer und Auftragsnummer.";
        }else{
            if(NUMBER_OF_UNITS!=0){
                units.add(Arrays.asList(
                          "INIT|"
                        + this.slotNumber.getText()
                        + "|"
                        + this.orderNumber.getText()
                ));
                --NUMBER_OF_UNITS;
            }
            colorOfMsg = "green";
            showCabinetStatus(NUMBER_OF_UNITS);
        }

        this.clearTextBoxes();
        this.showMessage(this.message,colorOfMsg);
    }

    public void clearTextBoxes(){
        this.slotNumber.clear();
        this.orderNumber.clear();
    }

    public void showCabinetStatus(int numberOfUnits){
        if(numberOfUnits!=0){
            this.message = "Das Gerät wurde erfolgreich hinzugefügt, gibt es noch " + numberOfUnits + " slots verfügbar.";
        }else{
            this.message = "Gibt es keine slots verfügbar, Die Initialisierung soll beginnen!";
        }
    }

    public void showMessage(String msg,String color){
        alertMessageInit.setStyle("-fx-text-fill:"+color+";");
        alertMessageInit.setText(msg);
    }
}