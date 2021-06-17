package com.imposters.team.model;

import com.imposters.team.App;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class User 
{
    private boolean adminStatus;
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty username;
    private SimpleStringProperty password;

    // constructors
    public User(){}

    public User(int id, String firstName, String lastName, String username, boolean administrator, String passwd)
    {
        this.id             = new SimpleIntegerProperty(id);
        this.password       = new SimpleStringProperty(passwd);
        this.lastName       = new SimpleStringProperty(lastName);
        this.username       = new SimpleStringProperty(username);
        this.firstName      = new SimpleStringProperty(firstName);
        this.adminStatus    = administrator;
    }

    public User(String userName, String passwd, boolean administrator)
    {
        this.password       = new SimpleStringProperty(passwd);
        this.username       = new SimpleStringProperty(userName);
        this.adminStatus    = administrator;
    }

    // Getter and setters of attributes

    public boolean isAdminStatus() 
    {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) 
    {
        this.adminStatus = adminStatus;
    }

    public int getId() 
    {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id.set(id);
    }

    public String getFirstName() 
    {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() 
    {
        return firstName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName.set(firstName);
    }

    public String getLastName() 
    {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() 
    {
        return lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName.set(lastName);
    }

    public String getUsername() 
    {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username.set(username);
    }

    public String getPassword() 
    {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password.set(password);
    }

    @Override
    public String toString() 
    {
        return "User{" +
                "adminStatus=" + adminStatus +
                ", id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", username=" + username +
                ", password=" + password +
                '}';
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id);
    }

    public String isAdminOrLimitedUser()
    {
        if(this.isAdminStatus())
        {
            return "Admin";
        }
        else
        {
            return "Limited User";
        }
    }
}
