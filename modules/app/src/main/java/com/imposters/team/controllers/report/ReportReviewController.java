package com.imposters.team.controllers.report;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.controllers.burn.UnitTestsInitializationController;
import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.stream.Collectors;


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
    private TableView<UnitUnderTest> table;
    @FXML
    private TableColumn<UnitUnderTest, Integer> slotNumber;
    @FXML
    private TableColumn<UnitUnderTest, Integer> orderNumber;
    @FXML
    private TableColumn<UnitUnderTest, Boolean> status;
    @FXML
    private TableColumn<UnitUnderTest, String> serialNumOfTheUnitTest;

    @Override
    @FXML
    public void nextClicked() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
        this.setBerichtIDInformation();
    }

    public void setBerichtIDInformation() {
        this.buildTable();
        this.fillTableWithInitializedTestingUnits();
        this.berichtID.setText("1");
        this.setDatum();
        this.setChamberIDAndArbeiterIDAndArbeitersvorname();
    }

    public void setDatum(){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        this.datum.setText(dtf.format(now));
    }

    public void setChamberIDAndArbeiterIDAndArbeitersvorname() {
        this.chamberID.setText(String.valueOf(Context.getEnvChamber().getEnvchamberID()));
        this.arbeiterID.setText(String.valueOf(Context.getUser().getId()));
        this.arbeitername.setText(Context.getUser().getLastName());
        this.arbeitersvorname.setText(Context.getUser().getFirstName());
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