package com.imposters.team.client;

import com.imposters.team.App;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class Sender {
    private int portNumber;
    private String hostName;

    private PrintWriter toServer;
    private BufferedReader in;
    private BufferedReader stdIn;

    private static ArrayList<String> sentMsg = new ArrayList<>();

    public Sender(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.socketInitializer();
    }

    public void socketInitializer(){
        try{
            Socket echoSocket = new Socket(this.hostName, this.portNumber);
            this.toServer =
                    new PrintWriter(echoSocket.getOutputStream(), true);

            this.in =
                    new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            this.stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
        }catch (IOException unknownHostException) {
            unknownHostException.printStackTrace();
        }
    }

    public void toServer(String msg) {
            this.toServer.println(msg);
            
    }

    public void setSentMsg(List<String> appendMsg){
        appendMsg.forEach(msg ->
            Sender.sentMsg.add(msg)
        );
    }

    public void sendMsgToMockServer(){
        App.getToServerSender().toServer(
                Sender.sentMsg
                        .stream()
                        .collect(Collectors.joining("|"))
        );
    }
}
