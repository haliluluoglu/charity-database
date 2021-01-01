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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class AccountingController implements Initializable{

    private  String balanceID;

    @FXML
    public Button btnSave = new Button();

    @FXML
    public Button btnClear = new Button();

    @FXML
    public Button btnCancel = new Button();

    @FXML
    public Button btnSearch = new Button();

    @FXML
    public TextField txtBalanceID = new TextField();

    @FXML
    public TextField txtbalanceType = new TextField();

    @FXML
    public TextField txtDescription = new TextField();

    @FXML
    public TextField txtBalanceDate = new TextField();

    @FXML
    public TextField txtAmount = new TextField();

    @FXML
    public TextField txtDeptID = new TextField();

    // Tableview Start
    @FXML
    private TableView<Accounting> tblAccounting;

    @FXML
    public TableColumn<Accounting,Integer> colBalanceID;
    @FXML
    public TableColumn<Accounting,String> colBalanceType;
    @FXML
    public TableColumn<Accounting,String> colDescription;
    @FXML
    public TableColumn<Accounting, Date> colBalanceDate;
    @FXML
    public TableColumn<Accounting,Double> colAmount;
    @FXML
    public TableColumn<Accounting,Integer> colDeptID;

    @FXML
    public TableColumn<Accounting,Boolean> colUpdate;
    @FXML
    public TableColumn<Accounting,Boolean> colDelete;
    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        loadTable(false);
    }

    public void saveNeedyForm(ActionEvent event)  {
        if(balanceID !=null)
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
        balanceID =null;
        btnCancel.setVisible(false);
    }

    private void clearForm(){
        txtBalanceID.setText("");
        txtbalanceType.setText("");
        txtDescription.setText("");
        txtBalanceDate.setText("");
        txtAmount.setText("");
        txtDeptID.setText("");
    }

    private void loadForm(Accounting accounting){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtBalanceID.setText(accounting.getBalanceID().toString());
        txtbalanceType.setText(accounting.getBalanceType());
        txtDescription.setText(accounting.getDescription());
        txtBalanceDate.setText(dateFormat.format(accounting.getBalanceDate()));
        txtAmount.setText(accounting.getAmount().toString());
        txtDeptID.setText(accounting.getDepartmentID().toString());
    }

    private void loadTable(Boolean filter){
        colBalanceID.setCellValueFactory(new PropertyValueFactory<>("balanceID"));
        colBalanceType.setCellValueFactory(new PropertyValueFactory<>("balanceType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBalanceDate.setCellValueFactory(new PropertyValueFactory<>("balanceDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDeptID.setCellValueFactory(new PropertyValueFactory<>("departmentID"));

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Accounting, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Accounting, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Accounting, Boolean>, TableCell<Accounting, Boolean>>() {
            @Override
            public TableCell<Accounting, Boolean> call(TableColumn<Accounting, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Accounting, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Accounting, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Accounting, Boolean>, TableCell<Accounting, Boolean>>() {
            @Override
            public TableCell<Accounting, Boolean> call(TableColumn<Accounting, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblAccounting.setItems(getAccountingList(filter));

        tblAccounting.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO Balance VALUES('"+
                txtBalanceID.getText().toString()+"','"+
                txtbalanceType.getText()+"','"+
                txtDescription.getText()+"','"+
                txtBalanceDate.getText()+"',"+
                txtAmount.getText().toString()+",'"+
                txtDeptID.getText().toString()+"'"+
                ")";
        executeQuery(query);
    }

    private void updateRecord(){
        String query = "UPDATE Balance SET "+
                "balance_id='"+ txtBalanceID.getText().toString()+"', "+
                "balance_type='"+ txtbalanceType.getText()+"', "+
                "description='"+ txtDescription.getText()+"', "+
                "balance_date='"+ txtBalanceDate.getText()+"', "+
                "amount="+ txtAmount.getText()+", "+
                "dept_id'"+ txtDeptID.getText()+"' "+
                " WHERE id_no='" + balanceID + "'";
        executeQuery(query);
        clearForm();
        btnCancel.setVisible(false);
        balanceID =null;
    }

    private void deleteRecord(String balanceID){
        String query = "DELETE FROM Balance WHERE id_no='"+balanceID+"'";
        executeQuery(query);
    }

    private ObservableList<Accounting> getAccountingList(Boolean filter){
        ObservableList<Accounting> accountings = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM balance ";

        if(filter){
            String condition="";

            if(txtBalanceID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" id_no='"+ txtBalanceID.getText()+"'";
            }
            if(txtbalanceType.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" fname LIKE '"+ txtbalanceType.getText()+"%'";
            }
            if(txtDescription.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" lname LIKE '"+ txtDescription.getText()+"%'";
            }
            if(txtAmount.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" income="+ txtAmount.getText();
            }
            if(txtDeptID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" email='"+ txtDeptID.getText()+"'";
            }
            if(condition!="")
                query += "WHERE "+condition;
        }

        Statement st;
        ResultSet rs;

        try {
            st= conn.createStatement();
            rs=st.executeQuery(query);
            Accounting accounting;
            while (rs.next()){

                accounting = new Accounting(
                        rs.getInt("balance_id"),
                        rs.getString("balance_type"),
                        rs.getString("description"),
                        rs.getDate("balance_date"),
                        rs.getDouble("amount"),
                        rs.getInt("dept_id"));
                accountings.add(accounting);
            }
            return  accountings;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private  void executeQuery(String query){
        try{
            Connection conn= new DatabaseConnection().getConnection();
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private class EditButtonCell extends TableCell<Accounting, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Accounting accounting = (Accounting) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    balanceID =accounting.getBalanceID().toString();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(accounting);
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

    private class DeleteButtonCell extends TableCell<Accounting, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Accounting accounting = (Accounting) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + accounting.getBalanceID()+" "+accounting.getBalanceType() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(accounting.getBalanceID().toString());
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
