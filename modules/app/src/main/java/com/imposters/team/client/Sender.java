package com.imposters.team.client;

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
        System.out.println(msg);
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

    public static void setSentMsg(List<String> appendMsg){
        appendMsg.forEach(msg ->
            Sender.sentMsg.add(msg)
        );
    }

    public static void sendMsgToMockServer(){

        new Thread(() ->
            App.getToServerSender().toServer
                    (
                            Sender.sentMsg.stream().collect
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
