package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @FXML
    public  Button btnLogin=new  Button();

    @FXML
    public  TextField txtUsername=new  TextField();

    @FXML
    public TextField txtPassword=new TextField();

    @FXML
    public AnchorPane apLogin=new AnchorPane();

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        try{

        }
        catch (Exception ex){

        }
    }

    public void checkLogin(ActionEvent event)throws Exception {
        String userName="admin";
        char password[]="123456".toCharArray();

        if(txtUsername.getText().equals(userName) && Arrays.equals(txtPassword.getText().toCharArray(),password)){
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene scene = new Scene(root,800,600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
        } else {
            Alert alert =new Alert(Alert.AlertType.WARNING,"Invalid username or password!",ButtonType.CLOSE);
            alert.show();
        }
    }
}
