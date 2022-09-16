package Noughts_and_Crosses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;


public class BoardView {

    private BoardController controller = new BoardController();
    private BoardModel model = new BoardModel();
    private ArrayList<TileModel> tiles = new ArrayList<>();

    // Nodes
    private BorderPane board = new BorderPane();
    private BorderPane grid = new BorderPane();
    private Text currentTurnText = new Text();
    private ImageView currentTurnImage = new ImageView();


//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        initialiseGrid();
//        initialiseBoard();
//        initialiseTiles();
//        initialiseStage(primaryStage);
//    }

    public BoardView(int boardSize) {
        model.setBoardSize(boardSize);
        initialiseGrid();
        initialiseBoard();
        initialiseTiles();
        initialiseStage(new Stage());
    }

    private void initialiseGrid() {
        grid = new BorderPane();
        HBox gridRow = new HBox();
        grid.setCenter(gridRow);
        gridRow.setAlignment(Pos.CENTER);
        for (int i = 0; i < model.getBoardSize(); i++) {
            VBox gridColumn = new VBox();
            for (int j = 0; j < model.getBoardSize(); j++) {
                Button button = new Button("", createLargeTile(model.getDefaultTileImage()));
                button.setOnAction(this::tileAction);
                button.setPadding(new Insets(5));
                gridColumn.getChildren().add(button);
            }
            gridRow.getChildren().add(gridColumn);
        }
    }

    private void initialiseBoard() {
        board.setCenter(grid);
        Button replaybutton = new Button("Replay");
        replaybutton.setOnAction(this::replay);
        replaybutton.setPadding(new Insets(5));
        Button closeButton = new Button("Close");
        closeButton.setOnAction(this::close);
        closeButton.setPadding(new Insets(5));
        currentTurnText.setText("Current turn: " + model.getWhoseTurnMarker());
        currentTurnImage = createSmallTile(model.getWhoseTurnPicture());
        HBox currentTurn = new HBox();
        currentTurn.getChildren().addAll(currentTurnText, currentTurnImage);
        board.setTop(currentTurn);
        HBox buttons = new HBox(replaybutton, closeButton);
        buttons.setAlignment(Pos.CENTER);
        board.setBottom(buttons);
    }
    private void nextTurn() {
        model.nextTurn();
        currentTurnText.setText("Current turn: " + model.getWhoseTurnMarker());
        currentTurnImage.setImage(model.getWhoseTurnPicture());
    }

    private void initialiseStage(Stage primaryStage) {
        primaryStage.setTitle("Noughts and Crosses");
        Scene scene = new Scene(board, model.getSceneSize(), model.getSceneSize());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void tileAction(ActionEvent event) {
        if(!tileExists(event)) {return;}
        Button button = (Button) event.getSource();
        button.setDisable(true);
        button.setGraphic(createLargeTile(model.getWhoseTurnPicture()));
        Pair location = getTileLocation(button);
        TileModel tile = getTile(location);
        tile.setMarker(model.getWhoseTurnMarker());
        String lastTurn = model.getWhoseTurnMarker();
        nextTurn();
        if (!hasWon(location)) {
            if (model.getNumberOfTurns() >= model.getBoardSize() * model.getBoardSize()) {
                showDraw();
                replay(event);
            }
            return;
        } else {
            showWinner(lastTurn);
            replay(event);
        }
    }

    private void showWinner(String winner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Winner!");
        alert.setHeaderText(winner + " have won");
        alert.setGraphic(createLargeTile(model.getPictureWithName(winner)));
        alert.showAndWait();
    }

    private void showDraw() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Draw!");
        alert.setHeaderText("It is a draw!");
        alert.showAndWait();
    }

    private void replay(ActionEvent event) {
        model.reset();
        initialiseGrid();
        initialiseBoard();
        initialiseTiles();
    }

    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private ImageView createLargeTile(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(model.getLargeTileSize());
        imageView.setFitWidth(model.getLargeTileSize());
        return imageView;
    }

    private ImageView createSmallTile(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(model.getSmallTileSize());
        imageView.setFitWidth(model.getSmallTileSize());
        return imageView;
    }

    private Pair<Integer, Integer> getTileLocation(Button tileLocation) {
        try {
            for (int i = 0; i < model.getBoardSize(); i++) {
                for (int j = 0; j < model.getBoardSize(); j++) {
                    Button button = (Button) ((VBox) ((HBox) grid.getCenter()).getChildren().get(i)).getChildren().get(j);
                    if (button.equals(tileLocation)) {
                        return new Pair<>(i, j);
                    }
                }
            }
            return new Pair<>(-1, -1);
        }
        catch (Exception e) {
            System.out.println("Error: Cannot find tile");
            return new Pair<>(-1, -1);
        }
    }

    private boolean tileExists(ActionEvent event) {
        try {
            Button tileLocation = (Button) event.getSource();
            for (int i = 0; i < model.getBoardSize(); i++) {
                for (int j = 0; j < model.getBoardSize(); j++) {
                    Button button = (Button) ((VBox) ((HBox) grid.getCenter()).getChildren().get(i)).getChildren().get(j);
                    if (button.equals(tileLocation)) {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (Exception e) {
            System.out.println("Error: Cannot find tile");
            return false;
        }
    }

    private TileModel getTile(Pair<Integer, Integer> location){
        for (TileModel tile:
             tiles) {
            if(tile.getLocation().equals(location)) return tile;
        } return new TileModel(location);
    }

    private ArrayList<Button> getNeighbourTiles(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        ArrayList<Pair<Integer, Integer>> neighbourLocations = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> notNeighbourLocations = new ArrayList<>();
        neighbourLocations.add(new Pair<Integer, Integer>(i-1, j-1));
        neighbourLocations.add(new Pair<Integer, Integer>(i, j-1));
        neighbourLocations.add(new Pair<Integer, Integer>(i+1, j-1));
        neighbourLocations.add(new Pair<Integer, Integer>(i-1, j));
        neighbourLocations.add(new Pair<Integer, Integer>(i+1, j));
        neighbourLocations.add(new Pair<Integer, Integer>(i-1, j+1));
        neighbourLocations.add(new Pair<Integer, Integer>(i, j+1));
        neighbourLocations.add(new Pair<Integer, Integer>(i+1, j+1));
        int boardSize = model.getBoardSize();
        for (Pair neighbourLocation:
             neighbourLocations) {
            if (!locationExist(neighbourLocation)) {
                notNeighbourLocations.add(neighbourLocation);
            }
        }
        for (Pair notNeighbourLocation:
             notNeighbourLocations) {
            neighbourLocations.remove(notNeighbourLocation);
        }
        ArrayList<Button> neighbours = null;
        return neighbours;
    }

    private boolean hasWon(Pair<Integer, Integer> location) {
        if (checkDownDiagonal(location)) return true;
        else if (checkUpDiagonal(location)) return true;
        else if (checkHorizontal(location)) return true;
        else if (checkVertical(location)) return true;
        else return false;
    }

    private int checkTopLeft(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair topLeft = new Pair<Integer, Integer>(i-1, j-1);
        if (locationExist(topLeft) &&
                sameMarker(getTile(topLeft), getTile(location))) {
            return checkTopLeft(topLeft) + 1;
        } return 0;
    }

    private int checkTopMiddle(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair topMiddle = new Pair<Integer, Integer>(i, j-1);
        if (locationExist(topMiddle) &&
                sameMarker(getTile(topMiddle), getTile(location))) {
            return checkTopMiddle(topMiddle) + 1;
        } return 0;
    }

    private int checkTopRight(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair topRight = new Pair<Integer, Integer>(i+1, j-1);
        if (locationExist(topRight) &&
                sameMarker(getTile(topRight), getTile(location))) {
            return checkTopRight(topRight) + 1;
        } return 0;
    }

    private int checkLeft(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair left = new Pair<Integer, Integer>(i-1, j);
        if (locationExist(left) &&
                sameMarker(getTile(left), getTile(location))) {
            return checkLeft(left) + 1;
        } return 0;
    }

    private int checkRight(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair right = new Pair<Integer, Integer>(i+1, j);
        if (locationExist(right) &&
                sameMarker(getTile(right), getTile(location))) {
            return checkRight(right) + 1;
        } return 0;
    }

    private int checkBottomLeft(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair bottomLeft = new Pair<Integer, Integer>(i-1, j+1);
        if (locationExist(bottomLeft) &&
                sameMarker(getTile(bottomLeft), getTile(location))) {
            return checkBottomLeft(bottomLeft) + 1;
        } return 0;
    }

    private int checkBottomMiddle(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair bottomMiddle = new Pair<Integer, Integer>(i, j+1);
        if (locationExist(bottomMiddle) &&
                sameMarker(getTile(bottomMiddle), getTile(location))) {
            return checkBottomMiddle(bottomMiddle) + 1;
        } return 0;
    }

    private int checkBottomRight(Pair<Integer, Integer> location) {
        int i = location.getKey();
        int j = location.getValue();
        Pair bottomRight = new Pair<Integer, Integer>(i+1, j+1);
        if (locationExist(bottomRight) &&
                sameMarker(getTile(bottomRight), getTile(location))) {
            return checkBottomRight(bottomRight) + 1;
        } return 0;
    }

    private boolean sameMarker(TileModel a, TileModel b) {
        return a.getMarker().equals(b.getMarker());
    }

    private boolean locationExist(Pair<Integer, Integer> location) {
        int boardSize = model.getBoardSize();
        if ((int)location.getKey() < 0 || (int)location.getValue() < 0 ||
                (int)location.getKey() >= boardSize || (int)location.getValue() >= boardSize) {
            return false;
        } else return true;
    }

    private void initialiseTiles() {
        tiles.clear();
        for (int i = 0; i < model.getBoardSize(); i++) {
            for (int j = 0; j < model.getBoardSize(); j++) {
                tiles.add(new TileModel(new Pair<>(i,j)));
            }
        }
    }

    private boolean checkDownDiagonal(Pair<Integer, Integer> location) {
        int length = 1;
        int goal = model.getBoardSize();
        length += checkTopLeft(location);
        length += checkBottomRight(location);
        return (length == goal);
    }

    private boolean checkUpDiagonal(Pair<Integer, Integer> location) {
        int length = 1;
        int goal = model.getBoardSize();
        length += checkBottomLeft(location);
        length += checkTopRight(location);
        return (length == goal);
    }

    private boolean checkHorizontal(Pair<Integer, Integer> location) {
        int length = 1;
        int goal = model.getBoardSize();
        length += checkLeft(location);
        length += checkRight(location);
        return (length == goal);
    }

    private boolean checkVertical(Pair<Integer, Integer> location) {
        int length = 1;
        int goal = model.getBoardSize();
        length += checkTopMiddle(location);
        length += checkBottomMiddle(location);
        return (length == goal);
    }
}
