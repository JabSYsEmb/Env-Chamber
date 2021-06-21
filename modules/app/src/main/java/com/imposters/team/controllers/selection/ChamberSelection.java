package com.imposters.team.controllers.selection;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.dao.CurveDao;
import com.imposters.team.dao.EnvChamberDao;
import com.imposters.team.model.EnvChamber;
import com.imposters.team.model.User;
import javafx.scene.control.ComboBox;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChamberSelection extends MainConfigurations implements Initializable {
    private User user;
    private List<EnvChamber> envChamberList;

    @FXML
    private ComboBox<String> chamberComboBox;
    @FXML
    private ComboBox<String> curveComboBox;

    @FXML
    public void dropDownClicked() {
        chamberComboBox.getItems().setAll(this.getEnvChamberIps());
    }

    @FXML
    public void dropDownCurveClicked() {
        curveComboBox.getItems().setAll(this.getCurveIds());
    }

    @FXML
    @Override
    public void nextClicked() {
        // Set the selected Chamber as ContextChamber to make it shareable across the project
        Context.setChamber(EnvChamberDao.getEnvChamberFromDatabase(
                chamberComboBox.getSelectionModel().getSelectedItem(), this.db));

        // set the selected Curve as ContextCurve to make it shareable across the project for further testing.
        Context.setCurve(CurveDao.getCurveFromDatabaseByTaskNumber(
                curveComboBox.getSelectionModel().getSelectedItem(), this.db));

        this.sendInitMsg();

        App.changeView("/fxml/burnIn-views/UnitTestsInitialization.fxml");
    }

    private void sendInitMsg() {
        String toServerMsg = this.client.messageJoiner(Arrays.asList(
                "STRT",
                chamberComboBox.getSelectionModel().getSelectedItem(),
                user.getUsername(),
                user.isAdminOrLimitedUser(),
                String.valueOf(Context.getEnvChamber().getFailureRate()),
                String.valueOf(Context.getEnvChamber().getAcceptedResponseTime())
        ));

        this.client.toServer(toServerMsg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Context.getUser();
        this.setStatusBar(user);
    }

    public List<String> getEnvChamberIps() {
        return EnvChamberDao.getEnvChamberFromDatabase(this.db)
                .stream()
                .map(item -> item.getIp())
                .collect(Collectors.toList());
    }

    public List<String> getCurveIds() {
        return CurveDao.getCurvesFromDatabase(this.db)
                .stream()
                .map(item -> item.getTaskNumber())
                .collect(Collectors.toList());
    }
}


