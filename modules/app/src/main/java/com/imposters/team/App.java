package com.imposters.team;

import com.imposters.team.controllers.loginViews.DataCatcher;
import com.imposters.team.db.MyJDBC;

import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.application.Application;

import javax.xml.crypto.Data;


/**
 * Hello world!
 *
 */
public class App extends Application{
    private static MyJDBC db;
    private static Stage primaryStageOfProgram;
    private static DataCatcher dataCatcher;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginViews/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStageOfProgram = primaryStage;
        primaryStageOfProgram.show();
    }

    public static void changeView(String fxmlPageDir){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource(fxmlPageDir));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStageOfProgram.setScene(scene);
            primaryStageOfProgram.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(App.class.getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public static Stage getPrimaryStageOfProgram(){

        return primaryStageOfProgram;
    }

    public static MyJDBC getDatabase(){
        return App.db;
    }
    public static DataCatcher getDataCatcher(){
        return App.dataCatcher;
    }

    public static void main( String[] args )
    {
//        db = new MyJDBC();
        App.dataCatcher = new DataCatcher("127.0.0.1",2332);
        launch(args);
    }
}
