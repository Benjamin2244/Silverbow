package Noughts_and_Crosses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private double sceneSize = 600;
    private double tileSize = (sceneSize-100)/boardSize;

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
        gridRow.setAlignment(Pos.CENTER);
        for (int i = 0; i < boardSize; i++) {
            VBox gridColumn = new VBox();
            for (int j = 0; j < boardSize; j++) {
                Button button = new Button("", createTile(defaultTile));
                button.setOnAction(this::tileAction);
                button.setPadding(new Insets(5));
                gridColumn.getChildren().add(button);
            }
            gridRow.getChildren().add(gridColumn);
        }
    }

    private void initialiseBoard() {
        board.setCenter(grid);
    }

    private void initialiseStage(Stage primaryStage) {
        primaryStage.setTitle("Noughts and Crosses");
        Scene scene = new Scene(board, sceneSize, sceneSize);
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
                        button.setGraphic(createTile(clickedTile));
                        return;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: Cannot find tile");
        }
    }

    private ImageView createTile(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(tileSize);
        imageView.setFitWidth(tileSize);
        return imageView;
    }
}
