package com.imposters.team.client;

import com.imposters.team.model.UnitUnderTest;
import sun.awt.datatransfer.DataTransferer;

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

public class Client
{
    private int portNumber;
    private String hostName;

    private Socket echoSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private BufferedReader stdIn;

    private List<String> sentMsg = new ArrayList<>();

    public Client(String hostName, int portNumber)
    {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.socketInitializer();
    }

    // socket initialization and establish a connection to the server via given port
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
        this.toServer.println(msg);
        String prefixMsg = new StringTokenizer(msg,"|").nextToken();
        String responseFromServer = this.fromServerMsg();

        switch (prefixMsg)
        {
            case "INIT":
            {
                this.initHandler(responseFromServer);
                break;
            }

            case "PRETST":
            {
                this.pretestHandler(responseFromServer);
                break;
            }

            case "OPERTEMP":
            {
                this.opertempHandler(responseFromServer);
                break;
            }

            case "PING":
            {
                this.pingHandler(responseFromServer);
                break;
            }

            default:
            {
                break;
            }

        }
    }

    private String fromServerMsg() {
        String gottenMsg;
        try{
            gottenMsg = this.fromServer.readLine();
        }
        catch (IOException io)
        {
            io.printStackTrace();
            gottenMsg = "Something's went wrong!";
        }
        return gottenMsg;
    }


    public void setSentMsg(List<String> appendMsg)
    {
        this.sentMsg.clear();
        appendMsg.forEach(msg -> this.sentMsg.add(msg));
    }

    public void sendMsgToMockServer()
    {
        this.toServer(
            this.sentMsg.stream()
                        .collect(Collectors.joining("|"))
        );
    }

    public void initHandler(String fromServerMsg)
    {
        String responsePattern = "Examinee <<(.*?)>> is registered in slot <<(.*?)>>";

        Matcher matcher = Pattern.compile(responsePattern).matcher(fromServerMsg);
        if(matcher.matches())
        {
            UnitUnderTest unitUnderTest = new UnitUnderTest(
                    Integer.parseInt(matcher.group(1)),
                    matcher.group(2));
            System.out.println(unitUnderTest.toString());
        }
    }

    private void opertempHandler(String readLine) {
        long startTime = System.nanoTime();
        System.out.println("calculate the response time in Milliseconds...");
        long stopTime = System.nanoTime();
        System.out.println("Execution time " + (stopTime - startTime) + "nano seconds");
    }

    private void pretestHandler(String test) {
        long startTime = System.nanoTime();
        System.out.println("calculate the response time in Milliseconds...");
        long stopTime = System.nanoTime();
        System.out.println("Execution time " + (stopTime - startTime) + "nano seconds");
        System.out.println("Get the temperature from the message that sent by the server.");
    }


    private void pingHandler(String responseFromServer) {
        System.out.println("Check the response failed Or Not and get the failure rate of the response");
    }
}
