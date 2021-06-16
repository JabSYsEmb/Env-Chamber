package com.imposters.team.client;

import com.imposters.team.App;
import com.imposters.team.model.Prufling;

import java.sql.Timestamp;
import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        try
        {
            String fromServerMsg = this.fromServer.readLine();
            this.messageProcessor(sent,fromServerMsg);
        }
        catch (IOException e)
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

    public void messageProcessor(String stage, String line){
        switch(stage)
        {
            case "INIT":
            {
                Matcher m = Pattern.compile("\\<<(.*?)\\>").matcher(line);
                while (m.find())
                {
//                    Prufling prufling = new Prufling("sdfsdf",m.group(0),m.group(1));
                }
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
    }
}
