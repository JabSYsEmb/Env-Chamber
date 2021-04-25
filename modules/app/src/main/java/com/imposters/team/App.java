package com.imposters.team;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.application.Application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App extends Application{
    protected static Stage primaryStageOfProgram;
    private String css;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginViews/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        css = App.class.getResource("/style/style.css").toExternalForm();
        root.getStylesheets().add(css);
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

    public static Stage getPrimaryStageOfProgram(){ return primaryStageOfProgram;}

    public static void main( String[] args )
    {
        launch(args);
    }
}
