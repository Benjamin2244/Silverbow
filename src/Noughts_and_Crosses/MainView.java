package Noughts_and_Crosses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



public class MainView extends Application {

    // Nodes
    private final BorderPane menu = new BorderPane();
    private final StackPane title = new StackPane();
    private final Text titleText = new Text("Noughts and Crosses");
    private final TextField customGameText = new TextField();
    private final HBox customGame = new HBox();
    private final VBox gameChoices = new VBox();
    private final Button customGameButton = new Button("Create");
    private final Text customGameHelp = new Text("Size of board: ");
    private final Button button2x2 = createButton("Play 2x2");
    private final Button button3x3 = createButton("Play 3x3");
    private final Button button4x4 = createButton("Play 4x4");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initialiseMenu();
        initialiseStage(primaryStage);
    }

    private void initialiseStage(Stage primaryStage) {
        primaryStage.setTitle("Noughts and Crosses");
        Scene scene = new Scene(menu, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialiseMenu() {
        initialiseTitle();
        menu.setTop(title);
        initialiseGameChoices();
        menu.setCenter(gameChoices);
    }

    private void initialiseTitle() {
        titleText.setFont(Font.font("Comic Sans MS", 25));
        titleText.setTextAlignment(TextAlignment.CENTER);
        title.getChildren().add(titleText);
    }

    private void initialiseGameChoices() {
        initialiseDefaultGameChoice();
        initialiseCustomGameChoice();
        gameChoices.getChildren().addAll(button2x2, button3x3, button4x4, customGame);
        gameChoices.setSpacing(5.0);
        gameChoices.setAlignment(Pos.CENTER);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPadding(new Insets(5));
        return button;
    }

    private void initialiseDefaultGameChoice() {
        button2x2.setOnAction(this::play2x2);
        button3x3.setOnAction(this::play3x3);
        button4x4.setOnAction(this::play4x4);
    }

    private void initialiseCustomGameChoice() {
        customGameText.setOnAction(actionEvent -> createCustomGame());
        customGameButton.setOnAction(actionEvent ->  createCustomGame());
        customGame.getChildren().addAll(customGameHelp, customGameText, customGameButton);
        customGame.setAlignment(Pos.CENTER);
        customGame.setSpacing(5);
    }

    private void play2x2(ActionEvent event) { new BoardView(2); }

    private void play3x3(ActionEvent event) { new BoardView(3); }

    private void play4x4(ActionEvent event) { new BoardView(4); }

    public String getCustomGameText() { return customGameText.getText(); }

    private void createCustomGame() {
        if (isValidCustomGame()) {
            new BoardView(Integer.parseInt(getCustomGameText()));
        }
    }

    private boolean isValidCustomGame() {
        String text = getCustomGameText();
        try {
            int num = Integer.parseInt(text);
            return num >= 1;
        } catch (Exception e) {
            return false;
        }
    }
}
