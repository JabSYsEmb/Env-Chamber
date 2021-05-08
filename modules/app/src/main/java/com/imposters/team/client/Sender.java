package com.imposters.team.client;



import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.UnknownHostException;

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
        } catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    } //try

    public void setSentMsg(ArrayList<String> appendMsg){
        appendMsg.forEach(msg -> {
            Sender.sentMsg.add(msg);
        });
    }

    public static String getSentMsg(){
        return Sender.sentMsg.toString();
    }

}
