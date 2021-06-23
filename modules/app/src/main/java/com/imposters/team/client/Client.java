package com.imposters.team.client;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
import com.sun.javafx.binding.StringFormatter;

import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Client {
    private int portNumber;
    private String hostName;

    private Socket echoSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private BufferedReader stdIn;

    private List<String> sentMsg = new ArrayList<>();

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.socketInitializer();
    }

    // socket initialization and establish a connection to the server via given port
    public void socketInitializer() {
        try {
            this.echoSocket = new Socket(this.hostName, this.portNumber);
            System.out.println("CLIENT: Connection to Server has been established successfully.");

            this.toServer =
                    new PrintWriter(echoSocket.getOutputStream(), true);

            this.fromServer =
                    new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            this.stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

        } catch (IOException unknownHostException) {
            unknownHostException.printStackTrace();
        }
    }

    public void toServer(String msg) {
        System.out.println("Sending - " + new StringTokenizer(msg).nextToken("|"));
        try {
            this.toServer.println(msg);
            this.fromServer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMessageFromServer(String toServerMsg) {
        System.out.println("Sending - " + new StringTokenizer(toServerMsg).nextToken("|"));
        this.toServer.println(toServerMsg);
        String gottenMsg;
        try {
            gottenMsg = this.fromServer.readLine();
        } catch (IOException io) {
            io.printStackTrace();
            gottenMsg = "Something's went wrong!";
        }
        return gottenMsg;
    }

    public String messageJoiner(List<String> separatedMsg) {
        return separatedMsg.stream().collect(Collectors.joining("|"));
    }

    public UnitUnderTest initHandler(String toServerMsg) {
        String fromServerMsg = this.getMessageFromServer(toServerMsg);

        String responsePattern = "Examinee <<(.*?)>> is registered in slot <<(.*?)>>";

        Matcher matcher = Pattern.compile(responsePattern).matcher(fromServerMsg);
        if (matcher.matches()) {
            UnitUnderTest unitUnderTest = new UnitUnderTest(
                    Integer.parseInt(matcher.group(2)),
                    matcher.group(1),
                    Context.getCurve().getTaskNumber(),
                    false);
            return unitUnderTest;
        } else {
            return null;
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


    public void pingHandler(UnitUnderTest unitUnderTest) {
        this.toServer("STRTBURNIN");
        this.toServer("SETTARGET|70.5|180|3|5");
        String toSeverMsg = "PING|" + unitUnderTest.getSlotId();
        long startTime = System.nanoTime();
        this.getMessageFromServer(toSeverMsg);
        long endTime = System.nanoTime();
        System.out.println(String.format("CLIENT: server has responded in {%d} ms",((endTime - startTime)/(1000000))));
        System.out.println("Check the response failed Or Not and get the failure rate of the response");
    }

    public boolean checkConnection() throws Exception {
        return this.echoSocket.isConnected();
    }
}
