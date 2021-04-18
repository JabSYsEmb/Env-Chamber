package com.imposters.team;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.application.Application;

/**
 * Hello world!
 *
 */
public class App extends Application{
    protected static Stage primaryStageOfProgram;
    private String css;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/loginViews/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        css = App.class.getResource("/style/style.css").toExternalForm();
        root.getStylesheets().add(css);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStageOfProgram = primaryStage;
        primaryStage.show();
    }

    public static Stage getPrimaryStageOfProgram(){ return primaryStageOfProgram;}

    public static void main( String[] args )
    {
        launch(args);
    }
}
