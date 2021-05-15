package com.imposters.team.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty id, firstName, lastName, username;
    private SimpleBooleanProperty administrator;

    public User(String id, String firstName, String lastName, String username, boolean administrator){
        this.id             = new SimpleStringProperty(id);
        this.firstName      = new SimpleStringProperty(firstName);
        this.lastName       = new SimpleStringProperty(lastName);
        this.username       = new SimpleStringProperty(username);
        this.administrator  = new SimpleBooleanProperty(administrator);
    }

    // Getter and setters of attributes
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public boolean isAdministrator() {
        return administrator.get();
    }

    @Override
    public String toString(){
        return (this.firstName.getValue() + " " + this.lastName.getValue());
    }
}
