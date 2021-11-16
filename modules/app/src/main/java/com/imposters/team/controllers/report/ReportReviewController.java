package com.imposters.team.controllers.report;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.controllers.burn.UnitTestsInitializationController;
import com.imposters.team.controllers.context.Context;
import com.imposters.team.dao.ReportDao;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.Report;
import com.imposters.team.model.Test;
import com.imposters.team.model.UnitUnderTest;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.stream.Collectors;


public class ReportReviewController extends MainConfigurations implements Initializable {

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

    private List<Test> testList = new ArrayList<>();

    @Override
    @FXML
    public void nextClicked() {
        this.insertBerichtIntoDatabase(this.reportCreator(), this.db);
        this.testListCreator(this.TestingUnits());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
        this.setBerichtIDInformation();
    }

    public boolean insertBerichtIntoDatabase(Report report, MyJDBC myJDBC) {
        if(ReportDao.setReportinDatabase(report, myJDBC)){
            return true;
        }else {
            return false;
        }
    }

    public Report reportCreator() {
        // User, EnvChamber, LocalDate, List<Test>
        Report report = new Report(
                Context.getUser(),
                Context.getEnvChamber(),
                LocalDateTime.now().toLocalDate(),
                testListCreator(this.TestingUnits()));
        return report;
    }

    public List<Test> testListCreator(List<UnitUnderTest> unitUnderTestList) {
        unitUnderTestList.stream().forEach(item ->
                        testList.add(new Test(
                                item.getSlotId(),
                                Context.getCurve(),
                                item,
                                item.isStatus(),
                                10
                        ))
                );
        return null;
    }

    public void setBerichtIDInformation() {
        this.buildTable();
        this.fillTableWithInitializedTestingUnits();
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
                this.TestingUnits()
        ));
    }

    public List<UnitUnderTest> TestingUnits(){
        return UnitTestsInitializationController.addedTestingUnits
                .stream()
                .filter((UnitUnderTest unitUnderTest) -> unitUnderTest != null)
                .collect(Collectors.toList());
    }
}
