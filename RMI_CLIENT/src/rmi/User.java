/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author kinut
 */
public class User implements Serializable{
    private String username,name,email,password;
    private ArrayList<Email> inbox;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public User(String username, String name, String email,String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.inbox = new ArrayList<>();
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Email> getInbox() {
        return inbox;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInbox(ArrayList<Email> inbox) {
        this.inbox = inbox;
    }
    public void addNewEmail(Email email){
        this.inbox.add(email);
    }
    public String toString(){
        return this.username+"~"+this.name+"~"+this.email+"~"+this.password;
    }
}
