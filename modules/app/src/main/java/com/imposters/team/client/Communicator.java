package com.imposters.team.client;

import com.imposters.team.App;

import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Communicator {
    private int portNumber;
    private String hostName;

    private Socket echoSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private BufferedReader stdIn;

    private static ArrayList<String> sentMsg = new ArrayList<>();

    public Communicator(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.socketInitializer();
    }

    public void socketInitializer(){
        try{
            this.echoSocket = new Socket(this.hostName, this.portNumber);
            this.toServer =
                    new PrintWriter(echoSocket.getOutputStream(), true);

            this.fromServer =
                    new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            this.stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

        }catch (IOException unknownHostException) {
            unknownHostException.printStackTrace();
        }
    }

    public void toServer(String msg) {
        this.toServer.println(msg);
        try {
            System.out.println(this.fromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSentMsg(List<String> appendMsg){
        Communicator.sentMsg.clear();
        appendMsg.forEach(msg ->
            Communicator.sentMsg.add(msg)
        );
    }

    public void sendMsgToMockServer(){
        App.getToServerSender().toServer(
                Communicator.sentMsg
                        .stream()
                        .collect(Collectors.joining("|"))
        );
    }
}
