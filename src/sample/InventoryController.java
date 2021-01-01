package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class InventoryController implements Initializable{

    private  String inventoryID;

    @FXML
    public Button btnSave= new Button();

    @FXML
    public Button btnClear= new Button();

    @FXML
    public Button btnCancel= new Button();

    @FXML
    public Button btnSearch= new Button();


    // Form Elements Start
    @FXML
    public TextField txtInventoryID=new TextField();

    @FXML
    public TextField txtInventoryType=new TextField();

    @FXML
    public TextField txtDescription=new TextField();

    @FXML
    public TextField txtAmount=new TextField();

    //Form Elements End

    // Tableview Start
    @FXML
    private TableView<Inventory> tblInventory;

    @FXML
    public TableColumn<Inventory,Integer> colInventoryID;
    @FXML
    public TableColumn<Inventory,String> colInventoryType;
    @FXML
    public TableColumn<Inventory,String> colDescription;
    @FXML
    public TableColumn<Inventory,Integer> colAmount;
    @FXML
    public TableColumn<Inventory,Boolean> colUpdate;
    @FXML
    public TableColumn<Inventory,Boolean> colDelete;
    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        loadTable(false);
    }

    public void saveInventoryForm(ActionEvent event)  {
        if(inventoryID !=null)
            updateRecord();
        else
            insertRecord();
        loadTable(false);
    }

    public void searchForm(ActionEvent event)  {
        loadTable(true);
        btnClear.setVisible(true);
    }

    public void clearFields(ActionEvent event)  {
        clearForm();
        btnClear.setVisible(false);
        loadTable(false);
    }

    public void cancelUpdate(ActionEvent event)  {
        clearForm();
        inventoryID =null;
        btnCancel.setVisible(false);
    }

    private void clearForm(){
        txtInventoryID.setText("");
        txtInventoryType.setText("");
        txtDescription.setText("");
        txtAmount.setText("");

    }

    private void loadForm(Inventory inventory){
        txtInventoryID.setText(inventory.getInventoryID().toString());
        txtInventoryType.setText(inventory.getInventoryType());
        txtDescription.setText(inventory.getDescription());
        txtAmount.setText(inventory.getAmount().toString());
    }

    private void loadTable(Boolean filter){
        colInventoryID.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        colInventoryType.setCellValueFactory(new PropertyValueFactory<>("inventoryType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Inventory, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Inventory, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Inventory, Boolean>, TableCell<Inventory, Boolean>>() {
            @Override
            public TableCell<Inventory, Boolean> call(TableColumn<Inventory, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Inventory, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Inventory, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Inventory, Boolean>, TableCell<Inventory, Boolean>>() {
            @Override
            public TableCell<Inventory, Boolean> call(TableColumn<Inventory, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblInventory.setItems(getInventoryList(filter));

        tblInventory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO needy VALUES('"+
                txtInventoryID.getText()+"','"+
                txtInventoryType.getText()+"','"+
                txtDescription.getText()+"','"+
                txtAmount.getText()+"','"+
                ")";
        executeQuery(query);
    }

    private void updateRecord(){
        String query = "UPDATE needy SET "+
                "inventory_id='"+ txtInventoryID.getText()+"', "+
                "inventory_type='"+txtInventoryType.getText()+"', "+
                "description='"+txtDescription.getText()+"', "+
                "amount='"+txtAmount.getText()+"', "+
                " WHERE id_no='" + inventoryID + "'";
        executeQuery(query);
        clearForm();
        btnCancel.setVisible(false);
        inventoryID =null;
    }

    private void deleteRecord(String identityNo){
        String query = "DELETE FROM Inventory WHERE id_no='"+identityNo+"'";
        executeQuery(query);
    }

    private ObservableList<Inventory> getInventoryList(Boolean filter){
        ObservableList<Inventory> inventories = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM inventory ";

        if(filter){
            String condition="";

            if(txtInventoryID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" inventory_id LIKE '"+txtInventoryID.getText()+"%'";
            }
            if(txtInventoryType.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" inventory_type LIKE '"+txtInventoryType.getText()+"%'";
            }
            if(txtDescription.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" description='"+txtDescription.getText()+"'";
            }
            if(txtAmount.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" amount="+txtAmount.getText();
            }
            if(condition!="")
                query += "WHERE "+condition;
        }

        Statement st;
        ResultSet rs;

        try {
            st= conn.createStatement();
            rs=st.executeQuery(query);
            Inventory inventory;
            while (rs.next()){

                inventory = new Inventory(
                        rs.getInt("inventory_id"),
                        rs.getString("inventory_type"),
                        rs.getString("description"),
                        rs.getInt("amount"));
                inventories.add(inventory);
            }
            return  inventories;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }

    private  void executeQuery(String query){
        try{
            Connection conn= new DatabaseConnection().getConnection();
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private class EditButtonCell extends TableCell<Inventory, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Inventory inventory = (Inventory) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    inventoryID =inventory.getInventoryID().toString();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(inventory);
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

    private class DeleteButtonCell extends TableCell<Inventory, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Inventory inventory = (Inventory) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + inventory.getInventoryID()+" "+inventory.getInventoryType() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(inventory.getInventoryID().toString());
                        loadTable(false);
                        clearForm();
                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

}
