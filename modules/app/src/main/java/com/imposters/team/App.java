package com.imposters.team;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.client.Communicator;


import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;


/** x
 * Hello world!
 *
 */
public class App extends Application
{
    private static MyJDBC db;
    private static Communicator sender;
    private static Stage primaryStageOfProgram;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStageOfProgram = primaryStage;
        primaryStageOfProgram.show();
    }

    public static void changeView(String fxmlPageDir)
    {
        try 
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource(fxmlPageDir));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStageOfProgram.setScene(scene);
            primaryStageOfProgram.show();
        } 
        catch (IOException e) 
        {
            Logger logger = Logger.getLogger(App.class.getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public static Stage getPrimaryStageOfProgram()
    {
        return primaryStageOfProgram;
    }

    public static MyJDBC getDatabase()
    {
        return App.db;
    }
    public static Communicator getToServerSender()
    {
        return App.sender;
    }

    public static void main( String[] args )
    {
        db = new MyJDBC();

        // Initialize a thread for communication with the server
        new Thread(() -> App.sender = new Communicator("127.0.0.1",1111))
                .start();

        launch(args);
    }
}

