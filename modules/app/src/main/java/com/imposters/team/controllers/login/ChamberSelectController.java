package com.imposters.team.controllers.login;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.EnvChamber;
import com.imposters.team.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ChamberSelectController extends UpperAnchorPaneFunctionalities
        implements Initializable {

    private User user;

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
        Context.setChamber(new EnvChamber(1,"172.16.103.136"));
        new Thread(() -> {
            App.getToServerSender()
                    .toServer("INIT|" +
                            chamberComboBox.getSelectionModel().getSelectedItem()
                            +"|1012323");
        });

        App.changeView("/fxml/burnIn-views/burnInTester.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Context.getUser();
        this.setStatusBar(user);
    }
}


