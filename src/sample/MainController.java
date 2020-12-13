package sample;

import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.event.ActionListener;

public class MainController {
    @FXML
    AnchorPane anchorPane = new AnchorPane();

    @FXML
    MenuButton menuButton = new MenuButton();
    @FXML
    MenuItem item1 = new MenuItem("Needy Table");
    @FXML
    MenuItem item2 = new MenuItem("Donators Table");
    @FXML
    MenuItem item3 = new MenuItem("Balance Table");
    @FXML
    MenuItem item4 = new MenuItem("Employee Table");
    @FXML
    MenuItem item5 = new MenuItem("Department Table");
    @FXML
    MenuItem item6 = new MenuItem("Members Table");
    @FXML
    MenuItem item7 = new MenuItem("Inventory Table");
    @FXML
    MenuItem item8 = new MenuItem("asdasd Table");

    @FXML
    ScrollPane scrollPane = new ScrollPane();

    @FXML
    Label label = new Label();

    @FXML
    Button addButton = new Button();
    @FXML
    Button deleteButton = new Button();
    @FXML
    Button editButton = new Button();

    public MainController() {
        menuButton.getItems().setAll(item1,item2,item3,item4,item5,item6,item7,item8);
    }

    public void setMenuButton(SplitMenuButton splitMenuButton) {
        this.menuButton = splitMenuButton;
    }

    @FXML
    public void menuButtonOnAction(MouseEvent event)
    {
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader object = new FXMLLoader();
                try
                {
                    System.out.println("sdasd");
                    anchorPane.getChildren().clear();
                    AnchorPane newPane = new FXMLLoader().load(getClass().getResource("needy.fxml"));
                    anchorPane.getChildren().add(newPane);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    @FXML
    private void addButtonAction(ActionEvent event)
    {
            label.setText("adasd");
    }


}
