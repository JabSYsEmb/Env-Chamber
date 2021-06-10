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

public class BurnInTesterController extends UpperAnchorPaneFunctionalities
        implements Initializable {

    private Communicator mySender;
    private int numberOfUnits;
    private String colorOfMsg;

    private List<List<String>> units = new ArrayList();

    private String message;
    @FXML
    private Label alertMessageInit;

    @FXML
    private TextField Artikelnummer;

    @FXML
    private TextField Auftragsnummer;

    @FXML
    @Override
    public void nextClicked() {

        this.units.stream().forEach(
                item -> this.mySender.toServer(
                        item.stream().collect(Collectors.joining()))
        );

        App.changeView("/fxml/burnIn-views/burnInTester2.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setStatusBar(Context.getUser(),Context.getEnvChamber());
        this.mySender = App.getToServerSender();
        this.numberOfUnits = 2;
    }

    @FXML
    public void addNewUnit(){
        this.message = "";
        this.colorOfMsg = "red";
        if(this.Artikelnummer.getText().isEmpty() || this.Auftragsnummer.getText().isEmpty())
        {
            this.message = "Ungültige Gerät, jedes Gerät sollte eine Artikelnummer und Auftragsnummer.";
        }else{
            if(numberOfUnits!=0){
                units.add(Arrays.asList(
                          "INIT|"
                        + this.Artikelnummer.getText()
                        + "|"
                        + this.Auftragsnummer.getText()
                ));
                --numberOfUnits;
            }
            this.colorOfMsg = "green";
            showCabinetStatus(numberOfUnits);
        }
        this.clearTextBoxes();
        this.showMessage(this.message,this.colorOfMsg);
    }

    public void clearTextBoxes(){
        this.Artikelnummer.clear();
        this.Auftragsnummer.clear();
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