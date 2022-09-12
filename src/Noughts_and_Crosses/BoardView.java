package Noughts_and_Crosses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BoardView extends Application {

    private int boardSize = 3;

    // Nodes
    private BorderPane board = new BorderPane();
    private BorderPane grid = new BorderPane();
    private Image defaultTile = new Image("/default.png");
    private Image clickedTile = new Image("/clicked.png");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initialiseGrid();
        initialiseBoard();
        initialiseStage(primaryStage);
    }

    private void initialiseGrid() {
        HBox gridRow = new HBox();
        grid.setCenter(gridRow);
        for (int i = 0; i < boardSize; i++) {
            VBox gridColumn = new VBox();
            for (int j = 0; j < boardSize; j++) {
                Button button = new Button("", new ImageView(defaultTile));
                button.setOnAction(this::tileAction);
                button.setPadding(new Insets(5));
                gridColumn.getChildren().add(button);
                System.out.println(i + " : " + j);
            }
            gridRow.getChildren().add(gridColumn);
        }
    }

    private void initialiseBoard() {
        board.setCenter(grid);
    }

    private void initialiseStage(Stage primaryStage) {
        primaryStage.setTitle("Noughts and Crosses");
        Scene scene = new Scene(board, 700, 625);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void tileAction(ActionEvent event) {
        try {
            Button tileLocation = (Button) event.getSource();
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    Button button = (Button) ((VBox) ((HBox) grid.getCenter()).getChildren().get(i)).getChildren().get(j);
                    if (button.equals(tileLocation)) {
                        System.out.println(i + " : " + j);
                        button.setDisable(true);
                        button.setGraphic(new ImageView(clickedTile));
                        return;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: Cannot find tile");
        }
    }
}
