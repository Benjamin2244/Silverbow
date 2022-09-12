package Silverbow;

// Javafx
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainView extends Application {

    private MainController controller = new MainController();
    private MainModel model = new MainModel();
    private boolean activate_seeder = true;
    private boolean only_seeder = false;
    private int number_of_extra_apps = 5;

    // Nodes
    private BorderPane root = new BorderPane();
    private BorderPane appViewer = new BorderPane();
    private VBox allApps = new VBox();
    private HBox searchBar = new HBox();
    private TextField searchBarText = new TextField();
    private ArrayList<String> orderTypes = new ArrayList<>();
    private ComboBox orderDropDown = new ComboBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialiseApps();
        initialiseRoot();
        initialiseAppViewer();
        initialiseStage(primaryStage);
    }

    private void initialiseApps() {
        if (!only_seeder) { model.addApps(); }
        if (activate_seeder) { model.addSeeder(number_of_extra_apps); }
        model.getApps().forEach(app -> addAppToList(allApps, app.getName()));
    }

    private void initialiseRoot() {
        root.setCenter(appViewer);
    }

    private void initialiseAppViewer() {
        initialiseSearchBar();
        appViewer.setTop(searchBar);
        appViewer.setCenter(allApps);
    }

    private void initialiseSearchBar() {
        searchBarText.setOnAction(actionEvent -> filterApps());
        Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent ->  filterApps());
        populateOrderTypes();
        ObservableList<String> list = FXCollections.observableArrayList(orderTypes);
        orderDropDown.setItems(list);
        orderDropDown.setOnAction(actionEvent -> filterApps());
        searchBar.getChildren().addAll(orderDropDown, searchBarText, searchButton);
        searchBar.setPadding(new Insets(10));
        searchBar.setSpacing(15);
        searchBar.setAlignment(Pos.CENTER);
    }

    private void populateOrderTypes() {
        orderTypes.add("AtoZ");
        orderTypes.add("ZtoA");
    }

    private boolean orderApps() {
        String selectedOrder = (String) orderDropDown.getValue();
        if ("AtoZ".equals(selectedOrder)) {
            setAtoZOrder();
        } else if ("ZtoA".equals(selectedOrder)) {
            setZtoAOrder();
        } else { return false; }
        return true;
    }

    public String getSearchBarText() {
        return searchBarText.getText();
    }

    private void filterApps() {
        if (!orderApps()) {
            removeApps();
            ArrayList<AppModel> filteredList = model.getSearchResults(getSearchBarText());
            filteredList.forEach(app -> addAppToList(allApps, app.getName()));
        }
    }

    private void setAtoZOrder() {
        removeApps();
        ArrayList<AppModel> orderedList = model.getAtoZSearch(getSearchBarText());
        orderedList.forEach(app -> addAppToList(allApps, app.getName()));
    }

    private void setZtoAOrder() {
        removeApps();
        ArrayList<AppModel> orderedList = model.getZtoASearch(getSearchBarText());
        orderedList.forEach(app -> addAppToList(allApps, app.getName()));
    }

    private void removeApps() {
        allApps.getChildren().clear();
    }

    private void initialiseStage(Stage primaryStage) {
        primaryStage.setTitle("Silverbow");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }

    private void addAppToList(Pane allApps, String name) {
        BorderPane appNameContainer = new BorderPane();
        appNameContainer.setPadding(new Insets(5));
        appNameContainer.setMaxWidth(150);
        Text appName = new Text(name);
        appNameContainer.setCenter(appName);
        appName.setWrappingWidth(100);
        appName.setTextAlignment(TextAlignment.CENTER);
        Button button = new Button("Click Here!");
        button.setOnAction(this::launchApp);
        button.setPadding(new Insets(5));

        HBox appRow = new HBox(appNameContainer, button);

        appRow.setPrefHeight(40);
        appRow.setAlignment(Pos.CENTER);
        allApps.getChildren().add(appRow);
    }

    private void launchApp(ActionEvent event) {
        try {
            String buttonName = ((Text) ((BorderPane) ((HBox) ((Button) event.getSource()).getParent()).getChildren().get(0)).getCenter()).getText();
            try {
                AppModel app = model.getApp(buttonName);
                Application runnable_app = app.getApplication().getClass().getDeclaredConstructor().newInstance();
                runnable_app.start(new Stage());
            }
            catch (Exception e) {
                System.out.println("Error");
            }
        }
        catch (Exception e) {
            System.out.println("Error: Cannot find name for the app");
        }
    }
}

