package Noughts_and_Crosses;

import SeederApp.SeederApp;
import Silverbow.AppModel;
import Silverbow.MainView;
import javafx.application.Application;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BoardModel {

    private final ArrayList<AppModel> apps = new ArrayList<>();
    private int boardSize = 3;
    private double sceneSize = 600;
    private double largeTileSize = (sceneSize-150)/boardSize;
    private double smallTileSize = (sceneSize-100)/20;
    private int numberOfTurns;
    private ArrayList<String> teams = new ArrayList<>();
    private HashMap<String, Image> teamToImage = new HashMap<>();
    private int whoseTurn;
    private Image defaultTileImage = new Image("/default.png");
    private Image clickedTileImage = new Image("/clicked.png");
    private Image noughtsImage = new Image("/nought.png");
    private Image crossesImage = new Image("/cross.png");

    public BoardModel() {
        initialiseTeams();
        initialiseTurn();
        initialiseTeamToImage();
    }

    public void setBoardSize(int boardSizeInput) {
        boardSize = boardSizeInput;
        largeTileSize = (sceneSize-150)/boardSize;
    }

    private void initialiseTeams(){
        teams.add("Noughts");
        teams.add("Crosses");
    }

    private void initialiseTeamToImage(){
        teamToImage.put("Noughts", noughtsImage);
        teamToImage.put("Crosses", crossesImage);
    }

    private void initialiseTurn(){
        whoseTurn = 0;
        numberOfTurns = 0;
    }

    public void reset(){
        initialiseTurn();
    }

    public void nextTurn(){
        numberOfTurns += 1;
        whoseTurn += 1;
        if (whoseTurn == teams.size()) whoseTurn = 0;
    }

    public int getNumberOfTurns() { return numberOfTurns; }

    public String getWhoseTurnMarker() {
        return teams.get(whoseTurn); }

    public Image getWhoseTurnPicture() {
        String player = getWhoseTurnMarker();
        return teamToImage.get(player);
    }

    public Image getPictureWithName(String player) {
        return teamToImage.get(player);
    }

    public Image getDefaultTileImage() { return defaultTileImage; }

    public int getBoardSize() {
        return boardSize;
    }

    public double getSceneSize() {
        return sceneSize;
    }

    public double getLargeTileSize() {
        return largeTileSize;
    }
    public double getSmallTileSize() {
        return smallTileSize;
    }
}
