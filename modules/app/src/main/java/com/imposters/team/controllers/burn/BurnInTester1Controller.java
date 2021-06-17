package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

public class BurnInTester1Controller extends UpperAnchorPaneFunctionalities implements Initializable 
        {
    private int NUMBER_OF_UNITS  = 2;

    private List<List<String>> units = new ArrayList<>();
    protected static List<List<String>> addedUnitsForTesting = new ArrayList<>();

    private String message;
    @FXML
    private Button AddTestingUnit;

    @FXML
    private Label alertMessageInit;
    @FXML
    private TextField slotNumber;

    @FXML
    private TextField orderNumber;

    @FXML
    @Override
    public void nextClicked() 
    {
        // copying the value into static list for sharing over Project
        addedUnitsForTesting = this.units;

        try
        {
            this.units.stream().forEach(
                item -> this.client.toServer(item.stream().collect(Collectors.joining()))
            );
            // Ending The initialization
            this.client.toServer("ENDINIT");
        }
        catch (NullPointerException ex)
        {
            ex.getMessage();
        }

        App.changeView("/fxml/burnIn-views/burnInTester2.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        this.setStatusBar(Context.getUser(),
                          Context.getEnvChamber());
    }

    @FXML
    public void addNewUnit()
    {
        this.message = "";
        String colorOfMsg = "red";
        if(this.slotNumber.getText().isEmpty() || this.orderNumber.getText().isEmpty())
        {
            this.message = "Ungültige Gerät, jedes Gerät sollte eine Artikelnummer und Auftragsnummer.";
        }
        else
        {
            if(NUMBER_OF_UNITS!=0)
            {
                this.addTheUnitToUnitTestingList();
            }
            colorOfMsg = "green";
            showCabinetStatus(NUMBER_OF_UNITS);
        }
        this.clearTextBoxes();
        this.showMessage(this.message,colorOfMsg);
    }

    public void clearTextBoxes()
    {
        this.slotNumber.clear();
        this.orderNumber.clear();
    }

    public void showCabinetStatus(int numberOfUnits)
    {
        if(numberOfUnits!=0)
        {
            this.message = "Das Gerät wurde erfolgreich hinzugefügt, gibt es noch " + numberOfUnits + " slots verfügbar.";
        }
        else
        {
            this.message = "Gibt es keine slots verfügbar, Die Initialisierung soll beginnen!";
            this.disableUnitAdderButtonAndTextBoxesIfCabinetFull();
        }
    }

    public void showMessage(String msg,String color)
    {
        alertMessageInit.setStyle("-fx-text-fill:"+color+";");
        alertMessageInit.setText(msg);
    }

    public void addTheUnitToUnitTestingList()
    {
        units.add(Arrays.asList(
                "INIT|"
                        + this.slotNumber.getText()
                        + "|"
                        + this.orderNumber.getText()
        ));
        --NUMBER_OF_UNITS;
    }

    public void disableUnitAdderButtonAndTextBoxesIfCabinetFull()
    {
        this.AddTestingUnit.setDisable(true);
        this.slotNumber.setDisable(true);
        this.orderNumber.setDisable(true);
    }

    public static List<List<String>> getUnits() 
    {
        return addedUnitsForTesting;
    }

}