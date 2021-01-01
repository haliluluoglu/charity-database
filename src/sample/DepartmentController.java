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


public class DepartmentController implements Initializable{

    private  Integer deptNo;

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
    public TextField txtDeptID=new TextField();

    @FXML
    public TextField txtDeptName=new TextField();

    @FXML
    public TextField txtMgrID=new TextField();

    //Form Elements End

    // Tableview Start
    @FXML
    private TableView<Department> tblDept;

    @FXML
    public TableColumn<Department,Integer> colDeptID;
    @FXML
    public TableColumn<Department,String> colDeptName;
    @FXML
    public TableColumn<Department,Integer> colMgrID;

    @FXML
    public TableColumn<Department,Boolean> colUpdate;
    @FXML
    public TableColumn<Department,Boolean> colDelete;

    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        loadTable(false);
    }

    public void saveDeptForm(ActionEvent event)  {
        if(deptNo !=null)
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
        deptNo =null;
        btnCancel.setVisible(false);
    }

    private void clearForm(){
        txtDeptID.setText("");
        txtDeptName.setText("");
        txtMgrID.setText("");

    }

    private void loadForm(Department department){
        txtDeptID.setText(department.getDepartmentID().toString());
        txtDeptName.setText(department.getDepartmentName());
        txtMgrID.setText(department.getManagerID().toString());
    }

    private void loadTable(Boolean filter){
        colDeptID.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        colDeptName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        colMgrID.setCellValueFactory(new PropertyValueFactory<>("managerID"));
        /*
        *
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        */

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Department, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Department, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Department, Boolean>, TableCell<Department, Boolean>>() {
            @Override
            public TableCell<Department, Boolean> call(TableColumn<Department, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Department, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Department, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Department, Boolean>, TableCell<Department, Boolean>>() {
            @Override
            public TableCell<Department, Boolean> call(TableColumn<Department, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblDept.setItems(getDeptList(filter));

        tblDept.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO needy VALUES('"+
                txtDeptID.getText()+"','"+
                txtDeptName.getText()+"','"+
                txtMgrID.getText()+"','"+
                ")";
        executeQuery(query);
    }

    private void updateRecord(){
        String query = "UPDATE needy SET "+
                "dept_id='"+ txtDeptID.getText()+"', "+
                "dept_name='"+txtDeptName.getText()+"', "+
                "dept_mgr_id='"+txtMgrID.getText()+"', "+
                " WHERE id_no='" + deptNo + "'";
        executeQuery(query);
        clearForm();
        btnCancel.setVisible(false);
        deptNo =null;
    }

    private void deleteRecord(Integer deptID){
        String query = "DELETE FROM Department WHERE dept_id='"+deptID+"'";
        executeQuery(query);
    }

    private ObservableList<Department> getDeptList(Boolean filter){
        ObservableList<Department> departments = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM Department ";

        if(filter){
            String condition="";

            if(txtDeptID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" fname LIKE '"+txtDeptID.getText()+"%'";
            }
            if(txtDeptID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" lname LIKE '"+txtDeptName.getText()+"%'";
            }
            if(txtMgrID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" id_no='"+txtMgrID.getText()+"'";
            }
            if(condition!="")
                query += "WHERE "+condition;
        }

        Statement st;
        ResultSet rs;

        try {
            st= conn.createStatement();
            rs=st.executeQuery(query);
            Department department;
            while (rs.next()){

                department = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("dept_mgr_id"));
                departments.add(department);
            }
            return  departments;
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
        }catch (Exception ex){
            System.out.println("Error:" +ex.getMessage());
        }
    }

    private class EditButtonCell extends TableCell<Department, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Department department = (Department) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    deptNo =department.getDepartmentID();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(department);
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

    private class DeleteButtonCell extends TableCell<Department, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Department department = (Department) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + department.getDepartmentID()+" "+department.getDepartmentName() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(department.getDepartmentID());
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
