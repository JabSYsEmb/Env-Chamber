package com.imposters.team.client;

import java.util.ArrayList;
import java.util.List;

public class Reciever {
    private ArrayList<String> recievedMsg = new ArrayList<>();

    public Reciever(List<String> recievedMsg){
        this.recievedMsg.addAll(recievedMsg);
    }

    public ArrayList<String> getRecievedMsg(){
        return this.getRecievedMsg();
    }
}
