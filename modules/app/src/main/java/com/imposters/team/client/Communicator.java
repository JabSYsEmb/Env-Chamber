package com.imposters.team.client;

import com.imposters.team.App;

import java.sql.Timestamp;
import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Communicator {
    private int portNumber;
    private String hostName;

    private Socket echoSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private BufferedReader stdIn;

    private List<String> sentMsg = new ArrayList<>();

    public Communicator(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.socketInitializer();
    }

    public void socketInitializer()
    {
        try
        {
            this.echoSocket = new Socket(this.hostName,this.portNumber);

            this.toServer =
                    new PrintWriter(echoSocket.getOutputStream(), true);

            this.fromServer =
                    new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            this.stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

        }
        catch (IOException unknownHostException)
        {
            unknownHostException.printStackTrace();
        }
    }

    public void toServer(String msg)
    {
        System.out.println(msg);
        StringTokenizer a = new StringTokenizer(msg,"|");
        String sent = a.nextToken();

        this.toServer.println(msg);
        try {
            System.out.println(this.fromServer.readLine());
            switch(sent){
                case "INIT":
                {
                    System.out.println("------------------");
                }
                case "PRETST":
                {
                    System.out.println("calculate the response time in Milliseconds...");
                }
                case "OPERTEMP":
                {
                    System.out.println("Get the temperature from the message that sent by the server.");
                }
                case "PING":
                {
                    System.out.println("Check the response failed Or Not and get the failure rate of the response");
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setSentMsg(List<String> appendMsg)
    {
        this.sentMsg.clear();
        appendMsg.forEach(msg ->
                this.sentMsg.add(msg)
        );
    }

    public void sendMsgToMockServer()
    {
        App.getToServerSender().toServer(
                this.sentMsg
                        .stream()
                        .collect(Collectors.joining("|"))
        );
    }
}
