package Silverbow;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {

    private MainModel model = new MainModel();

    public static void main(String[] args) {}

    public void launchApp(ActionEvent event) {
        String buttonName = ((Text) ((BorderPane) ((HBox) ((Button) event.getSource()).getParent()).getChildren().get(0)).getCenter()).getText();
        System.out.println(buttonName);
        System.out.println(model.getApps());
        try {
            System.out.println(model.getApp(buttonName));
        } catch (Exception e) {
            System.out.println("Error: cannot find app");
        }

        try {
            Application app = model.getApp(buttonName).getApplication().getClass().getDeclaredConstructor().newInstance();
            System.out.println(buttonName);
            app.start(new Stage());
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
}
