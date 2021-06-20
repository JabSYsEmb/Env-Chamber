package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.*;

public class UnitTestsInitializationController extends MainConfigurations implements Initializable
        {
    private int NUMBER_OF_UNITS  = 20;

    private List<String> units = new ArrayList<>();
    protected static List<UnitUnderTest> addedTestingUnits = new ArrayList<>();

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
        this.units.stream().forEach
        (
                item -> addedTestingUnits.add(this.client.initHandler(item))
        );

        // Ending The initialization
        this.client.toServer("ENDINIT");

        App.changeView("/fxml/burnIn-views/ValidationOfUnitTests.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        this.setStatusBar(
                Context.getUser(),
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
        units.add(
                "INIT|"
                + this.slotNumber.getText()
                + "|"
                + this.orderNumber.getText()
        );
        --NUMBER_OF_UNITS;
    }

    public void disableUnitAdderButtonAndTextBoxesIfCabinetFull()
    {
        this.AddTestingUnit.setDisable(true);
        this.slotNumber.setDisable(true);
        this.orderNumber.setDisable(true);
    }
}