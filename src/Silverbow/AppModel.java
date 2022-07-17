package Silverbow;

import javafx.application.Application;

public class AppModel {

    private String name;
    private Class app_class;
    private Application application;

    public AppModel(String input_name, Class input_app_class, Application input_application) {
        name = input_name;
        app_class = input_app_class;
        application = input_application;
    }

    public String getName() {
        return name;
    }

    public Application getApplication() {
        return application;
    }
}
