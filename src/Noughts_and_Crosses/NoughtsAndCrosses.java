package Noughts_and_Crosses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class NoughtsAndCrosses extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("1");
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Noughts and Crosses");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
