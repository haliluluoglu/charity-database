package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NeedyController {

    @FXML
    Button searchButton = new Button();
    @FXML
    Button addButton = new Button();
    @FXML
    Button deleteButton = new Button();
    @FXML
    Button editButton = new Button();
    @FXML
    TextField fnameTextField = new TextField();

    DatabaseConnection databaseConnection = new DatabaseConnection();


    public void searchButtonAction(ActionEvent event)
    {

    }

    private void search()
    {
        String ex_query="SELECT * FROM Needy";
        PreparedStatement p;
        try
        {
            DatabaseConnection connection = (DatabaseConnection) databaseConnection.getConnection();
            p = connection.getConnection().prepareStatement(ex_query);
            ResultSet r = p.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
