package view;

import controller.Controller;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage = new Stage();
    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        controller = Controller.getInstance(primaryStage);

    }

}
