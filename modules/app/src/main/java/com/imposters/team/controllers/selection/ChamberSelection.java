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
    public void DropDownClicked() {
        chamberComboBox.getItems().setAll(this.getEnvChamberIps());
    }

    @FXML
    @Override
    public void nextClicked() {
        Context.setChamber(EnvChamberDao.getEnvChamberFromDatabase(
                chamberComboBox.getSelectionModel().getSelectedItem(), this.db));

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

        new Thread(() -> this.client.toServer(toServerMsg)).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(CurveDao.getCurveFromDatabase(2, this.db));
        user = Context.getUser();
        this.setStatusBar(user);
        this.setEnvChamberList(EnvChamberDao.getEnvChamberFromDatabase(this.db));
    }

    public void setEnvChamberList(List<EnvChamber> envChamberList) {
        this.envChamberList = envChamberList;
    }

    public List<String> getEnvChamberIps() {
        return this.envChamberList
                .stream()
                .map(item -> item.getIp())
                .collect(Collectors.toList());
    }
}


