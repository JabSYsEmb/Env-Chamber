package com.imposters.team;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application{
    // Declare Variables
    private int count = 0;
    private Button button;
    private Text countText;
    private FlowPane panel;
    private static final double MAX_X = 600;
    private static final double MAX_Y = 300;

    /*
     *
     * Write function here
     *
     */
    @Override
    public void start(Stage primaryStage){

        // Initiate Variables
        count = 0;
        countText = new Text("Pushes: 0");

        panel = new FlowPane(countText);

        Group baseDemo = new Group( panel);

        Scene scene = new Scene(baseDemo, MAX_X, MAX_Y);
        scene.setFill(Color.WHITE);

        scene.setOnMouseClicked((event)->{
            count++;
            countText.setText("Pushes: " + count);
        });

        primaryStage.setTitle("Draw a Circle when Button is Pressed");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main( String[] args )
    {
        launch(args);
    }
}
