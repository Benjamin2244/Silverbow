package Silverbow;

import javafx.application.Application;

// Other
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Packages that contain the apps
import SeederApp.SeederApp;
import Noughts_and_Crosses.NoughtsAndCrosses;

public class MainModel {

    private ArrayList<AppModel> apps = new ArrayList<>();

    public void main(String[] args) {
        addApps();
    }

    // Edit here to add apps to the program
    // Must add:
    // 1: Title of the program
    // 2: The class
    // 3: An object created from the class
    public void addApps() {
        addApp("Noughts and Crosses", NoughtsAndCrosses.class, new NoughtsAndCrosses());
        addApp("Silverbow", MainView.class, new MainView());
    }

    private void addApp(String name, Class appClass, Application application) {
        AppModel app = new AppModel(name, appClass, application);
        apps.add(app);
    }

    public void addSeeder(int number) {
        for (int i = 0; i < number; i++) {
            String fake_name = "something";
            addApp(fake_name, SeederApp.class, new SeederApp());
        }
    }

    public ArrayList<AppModel> getApps() {
        return apps;
    }

    public AppModel getApp(String name) throws Exception {
        List<AppModel> filtered_apps = apps.stream().filter(app -> app.getName().equals(name)).collect(Collectors.toList());
        if (filtered_apps.size() == 0) {
            throw new Exception();
        } else {return  filtered_apps.get(0);}
    }
}
