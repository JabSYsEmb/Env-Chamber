package com.imposters.team.client;

import com.imposters.team.model.User;
import com.imposters.team.App;

import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class Sender {
    private int portNumber;
    private String hostName;
    private static ArrayList<String> sentMsg = new ArrayList<>();

    public Sender(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void toServer(String msg) {
        try (
                Socket echoSocket = new Socket(this.hostName, this.portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
        ) {
            out.println(msg);
        } catch (IOException unknownHostException) {
            unknownHostException.printStackTrace();
        }
    }

    public void setSentMsg(List<String> appendMsg){
        appendMsg.forEach(msg ->
            Sender.sentMsg.add(msg)
        );
    }

    public static void setSTRMessageForCabinetMock(User signedInUser, List<String> msgToBeSent){

        new Thread(() ->
            App.getToServerSender().toServer
                    (
                    msgToBeSent.stream().collect
                            (
                                    Collectors.joining("|")
                            )
                    )
            ).start();
    }

    public static String getSentMsg(){
        return Sender.sentMsg.toString();
    }

}
