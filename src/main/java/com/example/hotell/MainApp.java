package com.example.hotell;



import com.example.hotell.gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
