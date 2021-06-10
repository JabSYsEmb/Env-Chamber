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
    private Sender mySender;

    @FXML
    private ComboBox<String> chamberComboBox;

    @FXML
    public void DropDownClicked(){
        chamberComboBox.getItems().setAll(
                getEnvChamberIps()
        );
    }

    @FXML
    @Override
    public void nextClicked() {
        Context.setChamber(EnvChamberDao.getEnvChamberFromDatabase(
                chamberComboBox.getSelectionModel().getSelectedItem(),this.db
        ));

        mySender.setSentMsg(Arrays.asList(
                "STRT",
                chamberComboBox.getSelectionModel().getSelectedItem(),
                user.getUsername(),
                user.isAdminOrLimitedUser(),
                String.valueOf(Context.getEnvChamber().getFailureRate()),
                String.valueOf(Context.getEnvChamber().getAcceptedResponseTime())
        ));

        mySender.sendMsgToMockServer();

        App.changeView("/fxml/burnIn-views/burnInTester.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        user = Context.getUser();
        this.setDatabase();
        this.setStatusBar(user);
        this.setEnvChamberList(EnvChamberDao.getEnvChamberFromDatabase(this.db));
        this.mySender = App.getToServerSender();
    }

    public void setEnvChamberList(List<EnvChamber> envChamberList) {
        this.envChamberList = envChamberList;
    }

    public List<String> getEnvChamberIps(){
        return this.envChamberList
                .stream()
                .map(item -> item.getIp())
                .collect(Collectors.toList());
    }
}


