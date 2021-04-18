package com.imposters.team;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

/**
 * Hello world!
 *
 */
public class App extends Application{
    protected static Parent root;
    protected static Stage primaryStageOfProgram;
    private String css;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.root = FXMLLoader.load(getClass().getResource("/loginViews/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        css = App.class.getResource("/style/style.css").toExternalForm();
        root.getStylesheets().add(css);
        primaryStage.setScene(new Scene(this.root));
        primaryStage.setResizable(false);
        primaryStageOfProgram = primaryStage;
        primaryStage.show();
    }

    public static Parent getRoot(){
        return root;
    }
    public static Stage getPrimaryStageOfProgram(){ return primaryStageOfProgram;}
    public static void setCursor(Cursor style){
        root.setCursor(style);
    }

    public static void main( String[] args )
    {
        launch(args);
    }
}
