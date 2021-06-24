package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.clock.ClockController;
import com.imposters.team.dao.UnitUnderTestDao;
import javafx.scene.control.cell.PropertyValueFactory;
import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
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

    private ClockController clockController;

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
//        this.insertUnitUnderTestsIntoDatabase(
//
//        );
        App.changeView("/fxml/report/ReportReview.fxml");
    }

    public void insertUnitUnderTestsIntoDatabase() {
        UnitTestsInitializationController.addedTestingUnits
                .stream()
                .filter((UnitUnderTest unitUnderTest) -> unitUnderTest != null)
                .collect(Collectors.toList())
                .stream()
                .forEach(item -> {
                UnitUnderTestDao.insertUnitUnderTestIntoDB(item,this.db);
        }   );
    }

    @FXML
    public void startPingOverUnits() {
        table.getItems()
                .stream()
                .forEach(item -> this.client.pingHandler(item,Context.getEnvChamber().getFailureRate()));

        this.stopTheClock();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.startTheClock();
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
        this.buildTable();
        this.fillTableWithInitializedTestingUnits();
    }

    public void startTheClock() {
        this.clockController =  new ClockController(10, "Hi My Friend");
        this.clockController.run(this.clock);
    }
    public void stopTheClock() {
        this.clockController.stopStopwatch();
    }

    public void buildTable() {
        this.slotNumber.setCellValueFactory(new PropertyValueFactory<>("slotId"));
        this.orderNumber.setCellValueFactory(new PropertyValueFactory<>("curveTaskNumber"));
        this.status.setCellValueFactory(new PropertyValueFactory<>("status"));
        this.serialNumOfTheUnitTest.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
    }

    public void fillTableWithInitializedTestingUnits() {
        table.setItems(FXCollections.observableArrayList(
                UnitTestsInitializationController.addedTestingUnits
                        .stream()
                        .filter((UnitUnderTest unitUnderTest) -> unitUnderTest != null)
                        .collect(Collectors.toList())
        ));
    }
}