package com.imposters.team.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty id, firstName, lastName, username;
    private SimpleBooleanProperty administrator;
    private SimpleStringProperty password;

    public User(String id, String firstName, String lastName, String username, boolean administrator, String passwd){
        this.id             = new SimpleStringProperty(id);
        this.password       = new SimpleStringProperty(passwd);
        this.lastName       = new SimpleStringProperty(lastName);
        this.username       = new SimpleStringProperty(username);
        this.firstName      = new SimpleStringProperty(firstName);
        this.administrator  = new SimpleBooleanProperty(administrator);
    }

    public User(String userName, String passwd, boolean administrator){
        this.password       = new SimpleStringProperty(passwd);
        this.username       = new SimpleStringProperty(userName);
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

    public String getPassword() {
        return password.get();
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

    @Override
    public boolean equals(Object obj){
        User temp_user = (User) obj;
        return this.username.equals(temp_user.getUsername()) && this.password.equals(temp_user.getPassword());
    }
}
