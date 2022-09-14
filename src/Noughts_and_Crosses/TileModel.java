package Noughts_and_Crosses;

import javafx.application.Application;
import javafx.util.Pair;

public class TileModel {

    private String marker = "neutral";
    private Pair<Integer, Integer> location;

    public TileModel(Pair<Integer, Integer> newLocation) {
        location = newLocation;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String newMarker) { marker = newMarker; }

    public Pair<Integer, Integer> getLocation() { return location; }
}
