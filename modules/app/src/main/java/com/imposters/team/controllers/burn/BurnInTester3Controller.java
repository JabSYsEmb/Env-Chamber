package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.clock.ClockController;
import com.imposters.team.controllers.context.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class BurnInTester3Controller extends UpperAnchorPaneFunctionalities  implements Initializable {

    @FXML
    private Label alertMessageBurnIn;
    @FXML
    private Label clock;
    @FXML
    private Label massage;
    @FXML
    private TableView table;

    @FXML
    private TextField SlotTextField;

    @FXML
    private TextField BauteilIDTextField;
    @FXML
    private TextField AuftragsnummerTextField;


    /* todo
    *   1. storing all slots in burinInTest1 static List for sharing
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
        App.changeView("/fxml/burnIn-views/burnInTester4.fxml");
    }

    @FXML
    public void fertigBtnClicked() {
        ObservableList<String> courseData = FXCollections.observableArrayList(
                new String("Inheritance"),
                new String("Abstraction"),
                new String("Polymorphism"),
                new String("Generics"),
                new String("OOPS"),
                new String("Functions"));
        table.setItems(courseData);
    }
    public void hinzufügenBtnClicked()
    {
        alertMessageBurnIn.setText("Ich werde hinzufügt werden");

    }
    public void entfernenBtnClicked()
    {
        alertMessageBurnIn.setText("Ich werde entfernt werden");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ClockController(11,"Hi My Friend").run(this.clock,this.massage);
        this.setStatusBar(Context.getUser(),Context.getEnvChamber());
    }
}