package org.ps;


import javafx.application.Application;
import javafx.stage.Stage;
import org.ps.gui.controllers.MainWindowController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.setScene(new MainWindowController().getScene());
        primaryStage.setTitle("TipSpeak");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
