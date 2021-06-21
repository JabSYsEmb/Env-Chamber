package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.clock.ClockController;
import javafx.scene.control.cell.PropertyValueFactory;
import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class UnitTestsPingerController extends MainConfigurations implements Initializable {

    @FXML
    private Label alertMessageBurnIn;
    @FXML
    private Label clock;
    @FXML
    private Label massage;

    @FXML
    private TableView<UnitUnderTest> table;
    @FXML
    private TableColumn<UnitUnderTest, Integer> slotNumber;
    @FXML
    private TableColumn<UnitUnderTest, Integer> orderNumber;
    @FXML
    private TableColumn<UnitUnderTest, Boolean> status;
    @FXML
    private TableColumn<UnitUnderTest, String> serialNumOfTheUnitTest;

    @FXML
    private TextField SlotTextField;

    @FXML
    private TextField BauteilIDTextField;
    @FXML
    private TextField AuftragsnummerTextField;


    /* todo
     *   1. storing all slots in ValidationOfUnitTestsController static List for sharing
     *   2. Extracting the ID, slot number and order Number of the list
     *   3. Pinging to all slots
     *   4. showing the results in the ObservableList
     */
    @Override
    @FXML
    public void nextClicked() {
        String Slot = SlotTextField.getText();
        String BauteilID = BauteilIDTextField.getText();
        alertMessageBurnIn.setText("Ich bin eine Warnungsnachricht");
        App.changeView("/fxml/burnIn-views/UnitTestsStarter.fxml");
    }

    @FXML
    public void fertigBtnClicked() {
//        table.getItems().setAll(UnitTestsInitializationController.addedTestingUnits);
        table.setItems(FXCollections.observableArrayList(
                UnitTestsInitializationController.addedTestingUnits
                        .stream()
                        .filter((UnitUnderTest unitUnderTest) -> unitUnderTest != null)
                        .collect(Collectors.toList())
        ));
    }

    public void hinzufügenBtnClicked() {
        alertMessageBurnIn.setText("Ich werde hinzufügt werden");
    }

    public void entfernenBtnClicked() {
        alertMessageBurnIn.setText("Ich werde entfernt werden");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ClockController(10, "Hi My Friend").run(this.clock, this.massage);
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
        this.buildTable();
    }

    public void buildTable() {
        this.slotNumber.setCellValueFactory(new PropertyValueFactory<>("slotId"));
        this.orderNumber.setCellValueFactory(new PropertyValueFactory<>("curveTaskNumber"));
        this.status.setCellValueFactory(new PropertyValueFactory<>("status"));
        this.serialNumOfTheUnitTest.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
    }
}