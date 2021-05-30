package com.imposters.team.controllers.login;

import com.imposters.team.App;
import com.imposters.team.client.Sender;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.EnvChamber;
import com.imposters.team.model.User;
import com.imposters.team.model.dao.EnvChamberDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChamberSelectController extends UpperAnchorPaneFunctionalities
        implements Initializable {

    private User user;
    private List<EnvChamber> envChamberList;

    @FXML
    private ComboBox<String> chamberComboBox;

    @FXML
    public void DropDownClicked(){
        chamberComboBox.getItems().setAll(
                "1.1.1.1.1",
                "ldskjfsdf",
                "sdljfjsdf",
                "sjdlf"
        );
    }

    @FXML
    @Override
    public void nextClicked() {
        Sender.sendMsgToMockServer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


