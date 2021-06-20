package com.imposters.team.client;

import com.imposters.team.db.DbConnectionEstablish;

public class ClientConnectionEstablish {

    protected Client client;

    private static volatile ClientConnectionEstablish instance;

    public static ClientConnectionEstablish getInstance(){
        if(instance == null) {
            synchronized (DbConnectionEstablish.class){
                instance = new ClientConnectionEstablish();
            }
        }
        return instance;
    }

    private ClientConnectionEstablish(){
        this.client = new Client("127.0.0.1",2332);
    }

    public Client getClient() {
        return this.client;
    }
}
