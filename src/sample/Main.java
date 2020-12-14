package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
//        MainController mainController = new MainController();
//        SplitMenuButton splitMenuButton = new SplitMenuButton();
//        splitMenuButton.setText("tab");
//        mainController.setSplitMenuButton(splitMenuButton);
        primaryStage.setTitle("Charity Database");
        Scene scene = new Scene(root,800,600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
