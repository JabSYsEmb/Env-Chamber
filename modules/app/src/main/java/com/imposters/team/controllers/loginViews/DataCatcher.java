package com.imposters.team.controllers.loginViews;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataCatcher {
    private int portNumber;
    private String hostName;

    public DataCatcher(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void emptyFunc(String userName) {
        try (
                Socket echoSocket = new Socket(this.hostName, this.portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
        ) {
            out.println("STRT|CabinetControl1|" + userName + "|Admin|10");
        } catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    } //try
}
