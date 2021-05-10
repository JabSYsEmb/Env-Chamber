package com.imposters.team.controllers.login;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ChamberSelectController extends UpperAnchorPaneFunctionalities {

    @FXML
    private Button chamberSelectingButton;

    @FXML
    private ComboBox<String> chamberComboBox;

    @FXML
    public void DropDownClicked(){
        chamberComboBox.getItems().setAll(
            "Cabinet-1",
            "Cabinet-2",
            "Cabinet-3",
            "Cabinet-4",
            "..."
        );
    }

    @FXML
    public void onChamberSelectingbtnClicked() {
        new Thread(() -> {
            App.getToServerSender()
                    .toServer("INIT|" +
                            chamberComboBox.getSelectionModel().getSelectedItem()
                            +"|1012323");
        });
        App.changeView("/burnInViews/burnInTester.fxml");
    }

}


