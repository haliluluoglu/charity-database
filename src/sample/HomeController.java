package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    private  String activeTab;
    @FXML
    Button btnLoadNeedy = new Button();

    @FXML
    Button btnLoadDonator = new Button();

    @FXML
    Button btnLoadMember = new Button();

    @FXML
    Button btnLoadEmployee = new Button();

    @FXML
    Button btnLoadDepartment = new Button();

    @FXML
    Button btnLoadInventory = new Button();

    @FXML
    Button btnLoadAccounting = new Button();

    @FXML
    Pane paneMain;

    public void loadAllAction(ActionEvent event) throws Exception {
        if(activeTab!=null){
            deactivateTabClass();
            clearPane();
        }
        switch (((Button)event.getSource()).getId()){
            case "btnLoadNeedy": loadNeedy(); break;
            case "btnLoadDonator": loadDonator(); break;
            case "btnLoadMember": loadMember(); break;
            case "btnLoadEmployee": loadEmployee(); break;
            case "btnLoadDepartment": loadDepartment(); break;
            case "btnLoadInventory": loadInventory(); break;
            case "btnLoadAccounting": loadAccounting(); break;
        }
        activateTabClass();
    }

    private  void clearPane() throws  Exception {
        paneMain.getChildren().remove(0);
    }
    private void loadNeedy() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("needies.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="needy";
    }

    private void loadDonator() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("donators.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="donator";
    }


    private void loadMember() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("members.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="member";

    }
    private void loadEmployee() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("employee.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="employee";

    }
    private void loadDepartment() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("department.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="department";

    }
    private void loadInventory() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("inventory.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="inventory";

    }
    private void loadAccounting() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("accounting.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="account";

    }

    private void activateTabClass(){
        switch (activeTab){
            case "needy":
                btnLoadNeedy.getStyleClass().add("main-btn-active");
                break;
            case "donator":
                btnLoadDonator.getStyleClass().add("main-btn-active");
                break;
            case "member":
                btnLoadMember.getStyleClass().add("main-btn-active");
                break;
            case "employee":
                btnLoadEmployee.getStyleClass().add("main-btn-active");
                break;
            case "department":
                btnLoadDepartment.getStyleClass().add("main-btn-active");
                break;
            case "inventory":
                btnLoadInventory.getStyleClass().add("main-btn-active");
                break;
            case "account":
                btnLoadAccounting.getStyleClass().add("main-btn-active");
                break;
        }
    }
    private  void deactivateTabClass(){
        switch (activeTab){
            case "needy":btnLoadNeedy.getStyleClass().remove("main-btn-active"); break;
            case "donator":btnLoadDonator.getStyleClass().remove("main-btn-active"); break;
            case "member":btnLoadMember.getStyleClass().remove("main-btn-active"); break;
            case "employee":btnLoadEmployee.getStyleClass().remove("main-btn-active"); break;
            case "department":btnLoadDepartment.getStyleClass().remove("main-btn-active"); break;
            case "inventory":btnLoadInventory.getStyleClass().remove("main-btn-active"); break;
            case "account":btnLoadAccounting.getStyleClass().remove("main-btn-active"); break;
        }

    }
}
