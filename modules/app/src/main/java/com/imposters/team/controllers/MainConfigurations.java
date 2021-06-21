package com.imposters.team.controllers;

import com.imposters.team.App;

import com.imposters.team.client.ClientConnectionEstablish;
import com.imposters.team.db.DbConnectionEstablish;
import com.imposters.team.model.EnvChamber;
import com.imposters.team.client.Client;
import javafx.scene.input.MouseEvent;
import com.imposters.team.model.User;
import com.imposters.team.db.MyJDBC;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

public abstract class MainConfigurations {

    // Establish a connection to the database
    protected MyJDBC db = DbConnectionEstablish.getInstance().getDb();

    // Connect the app to the server
    protected Client client = ClientConnectionEstablish.getInstance().getClient();
    @FXML
    protected Label statusChamber;
    @FXML
    protected Label statusUser;
    @FXML
    protected Label statusAdmin;
    private double initialX;
    private double initialY;

    @FXML
    public void onCloseClicked() {
        App.getPrimaryStageOfProgram().close();
    }

    @FXML
    public void onMinimizingClicked() {
        App.getPrimaryStageOfProgram().setIconified(true);
    }

    public void setOnMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
    }

    public void setOnMouseDragged(MouseEvent event) {
        App.getPrimaryStageOfProgram().setX(event.getScreenX() - initialX);
        App.getPrimaryStageOfProgram().setY(event.getScreenY() - initialY);
    }

    public void setStatusBar(User signedInUser) {
        try {
            if (signedInUser.isAdminStatus()) {
                statusAdmin.setText("Admin");
            } else {
                statusAdmin.setText("Limited User");
            }
            statusUser.setText(signedInUser.getFirstName() + " " + signedInUser.getLastName());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    public void setStatusBar(User signedInUser, EnvChamber chamber) {
        try {
            this.setStatusBar(signedInUser);
            statusChamber.setText(chamber.getIp());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    public void onKeyPressedListener(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ENTER:
                nextClicked();
        }
    }

    protected abstract void nextClicked();

}