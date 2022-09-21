package Noughts_and_Crosses;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardModel {

    private int boardSize = 3;
    private final double sceneSize = 600;
    private double largeTileSize = (sceneSize-150)/boardSize;
    private int numberOfTurns;
    private final ArrayList<String> teams = new ArrayList<>();
    private final HashMap<String, Image> teamToImage = new HashMap<>();
    private int whoseTurn;
    private final Image defaultTileImage = new Image("/default.png");
    private final Image noughtsImage = new Image("/nought.png");
    private final Image crossesImage = new Image("/cross.png");

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
        return getPictureWithName(player);
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
        return (sceneSize - 100) / 20;
    }
}
