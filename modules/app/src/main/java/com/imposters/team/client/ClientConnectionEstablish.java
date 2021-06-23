package com.imposters.team.client;

import com.imposters.team.db.DbConnectionEstablish;

public class ClientConnectionEstablish {
    // Singleton Design Pattern Applied in to avoid initializing more then one Connection to the Server.
    private static volatile ClientConnectionEstablish instance;
    protected Client client;

    private ClientConnectionEstablish() {
        this.client = new Client("127.0.0.1", 1111);
    }

    public static ClientConnectionEstablish getInstance() {
        if (instance == null) {
            synchronized (DbConnectionEstablish.class) {
                instance = new ClientConnectionEstablish();
            }
        }
        return instance;
    }

    public Client getClient() {
        return this.client;
    }
}
