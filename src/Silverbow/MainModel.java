package Silverbow;

import javafx.application.Application;

// Other
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Packages that contain the apps
import SeederApp.SeederApp;
import Noughts_and_Crosses.NoughtsAndCrosses;

public class MainModel {

    private final ArrayList<AppModel> apps = new ArrayList<>();

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
            String fake_name = "Demo app " + i;
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

    public ArrayList<AppModel> getSearchResults(String search) {
        return new ArrayList<>(getApps().stream().filter(app -> app.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList()));
    }

    public ArrayList<AppModel> getAtoZSearch(String search) {
        ArrayList<AppModel> filteredList = getSearchResults(search);
        int listSize = filteredList.size();
        int unorderedSize = listSize;
        while (unorderedSize > 0) {
            unorderedSize--;
            for (int i = 0; i < listSize - 1; i++) {
                if (isFirstAfterSecondAlphabetical(filteredList, i)) {
                    AppModel buffer = filteredList.get(i + 1);
                    filteredList.set(i + 1, filteredList.get(i));
                    filteredList.set(i, buffer);
                }
            }
        }
        return filteredList;
    }

    public ArrayList<AppModel> getZtoASearch(String search) {
        ArrayList<AppModel> filteredList = getSearchResults(search);
        int listSize = filteredList.size();
        int unorderedSize = listSize;
        while (unorderedSize > 0) {
            unorderedSize--;
            for (int i = 0; i < listSize - 1; i++) {
                if (!isFirstAfterSecondAlphabetical(filteredList, i)) {
                    AppModel buffer = filteredList.get(i + 1);
                    filteredList.set(i + 1, filteredList.get(i));
                    filteredList.set(i, buffer);
                }
            }
        }
        return filteredList;
    }

    private boolean isFirstAfterSecondAlphabetical(ArrayList<AppModel> list, int i)
    {
        String A = list.get(i).getName();
        String B = list.get(i+1).getName();
        int result = A.compareToIgnoreCase(B);
        return result > 0;
    }
}
