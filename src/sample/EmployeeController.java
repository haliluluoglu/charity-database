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


public class EmployeeController implements Initializable{

    private  String identityNo;

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
    public TextField txtFName=new TextField();

    @FXML
    public TextField txtLName=new TextField();

    @FXML
    public TextField txtIdentityNo=new TextField();

    @FXML
    public ChoiceBox cbMaritalStatus=new ChoiceBox();

    @FXML
    public ChoiceBox cbGender=new ChoiceBox();

    @FXML
    public TextField txtBirthDay=new TextField();

    @FXML
    public TextField txtStartDay=new TextField();

    @FXML
    public TextField txtSalary=new TextField();

    @FXML
    public TextField txtAddress=new TextField();

    @FXML
    public TextField txtPhone=new TextField();
    @FXML
    public TextField txtMail=new TextField();
    @FXML
    public TextField txtTitle=new TextField();

    //Form Elements End

    // Tableview Start
    @FXML
    private TableView<Employee> tblEmployees;
    @FXML
    public TableColumn<Employee,Integer> colDeptID;
    @FXML
    public TableColumn<Employee,String> colFName;
    @FXML
    public TableColumn<Employee,String> colLName;
    @FXML
    public TableColumn<Employee,String> colIdentityNo;
    @FXML
    public TableColumn<Employee,String> colMaritalStatus;
    @FXML
    public TableColumn<Employee,String> colGender;
    @FXML
    public TableColumn<Employee, Date> colBirthDate;
    @FXML
    public TableColumn<Employee, Date> colStartDate;
    @FXML
    public TableColumn<Employee,Integer> colSalary;
    @FXML
    public TableColumn<Employee,String> colAddress;
    @FXML
    public TableColumn<Employee,String> colPhoneNumber;
    @FXML
    public TableColumn<Employee,String> colEMail;
    @FXML
    public TableColumn<Employee,String> colTitle;
    @FXML
    public TableColumn<Employee,Boolean> colUpdate;
    @FXML
    public TableColumn<Employee,Boolean> colDelete;
    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){
        loadTable(false);
    }

    public void saveEmployeeForm(ActionEvent event)  {
        if(identityNo!=null)
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
        identityNo=null;
        btnCancel.setVisible(false);
    }

    private void clearForm(){
        txtDeptID.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtIdentityNo.setText("");
        cbMaritalStatus.setValue(null);
        cbGender.setValue(null);
        txtBirthDay.setText("");
        txtStartDay.setText("");
        txtSalary.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtMail.setText("");
        txtTitle.setText("");
    }

    private void loadForm(Employee employee){
        String maritalStat=null;
        String gender=null;
        switch (employee.getMaritalStat()){
            case "S": maritalStat="Single";break;
            case "M": maritalStat="Married";break;
            case "W": maritalStat="Widowed";break;
            case "D": maritalStat="Divorced";break;
        }
        switch (employee.getGender()){
            case "F": gender="Female";break;
            case "M": gender="Male";break;

        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtDeptID.setText(employee.getDeptID().toString());
        txtFName.setText(employee.getFname());
        txtLName.setText(employee.getLname());
        txtIdentityNo.setText(employee.getIdNo());
        cbMaritalStatus.setValue(maritalStat);
        cbGender.setValue(gender);
        txtBirthDay.setText(dateFormat.format(employee.getBirthDate()));
        txtStartDay.setText(dateFormat.format(employee.getStartDate()));
        txtSalary.setText(employee.getSalary().toString());
        txtAddress.setText(employee.getAddress());
        txtPhone.setText(employee.getPhoneNum());
        txtMail.setText(employee.getEmail());
        txtTitle.setText(employee.getTitle());
    }

    private void loadTable(Boolean filter){
        colDeptID.setCellValueFactory(new PropertyValueFactory<>("deptID"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        colIdentityNo.setCellValueFactory(new PropertyValueFactory<>("idNo"));
        colMaritalStatus.setCellValueFactory(new PropertyValueFactory<>("maritalStat"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
//        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
//        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
//        colEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Employee, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Employee, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>>() {
            @Override
            public TableCell<Employee, Boolean> call(TableColumn<Employee, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Employee, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Employee, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>>() {
            @Override
            public TableCell<Employee, Boolean> call(TableColumn<Employee, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblEmployees.setItems(getEmployeeList(filter));

        tblEmployees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO Employee VALUES('"+
                txtDeptID.getText()+"','"+
                txtFName.getText()+"','"+
                txtLName.getText()+"','"+
                txtIdentityNo.getText()+"','"+
                cbMaritalStatus.getValue().toString().charAt(0)+"','"+
                cbGender.getValue().toString().charAt(0)+"','"+
                txtBirthDay.getText()+"',"+
                txtStartDay.getText()+"',"+
                txtSalary.getText()+"',"+
                txtAddress.getText()+"','"+
                txtPhone.getText()+"','"+
                txtMail.getText()+"'"+
                txtTitle.getText()+"'"+
                ")";
        executeQuery(query);
    }

    private void updateRecord(){
        String query = "UPDATE Employee SET "+
                "deptID='"+ txtDeptID.getText()+"', "+
                "fname='"+ txtFName.getText()+"', "+
                "lname='"+txtLName.getText()+"', "+
                "id_no='"+txtIdentityNo.getText()+"', "+
                "marital_stat='"+cbMaritalStatus.getValue().toString().charAt(0)+"', "+
                "gender='"+cbGender.getValue().toString().charAt(0)+"', "+
                "bdate='"+txtBirthDay.getText()+"', "+
                "sdate='"+txtStartDay.getText()+"', "+
                "salary='"+txtSalary.getText()+"', "+
                "address='"+txtAddress.getText()+"', "+
                "phone_num='"+txtPhone.getText()+"', "+
                "email='"+txtMail.getText()+"' "+
                "title='"+txtTitle.getText()+"' "+
                " WHERE id_no='" + identityNo + "'";
        executeQuery(query);
        clearForm();
        btnCancel.setVisible(false);
        identityNo=null;
    }

    private void deleteRecord(String identityNo){
        String query = "DELETE FROM Employee WHERE id_no='"+identityNo+"'";
        executeQuery(query);
    }

    private ObservableList<Employee> getEmployeeList(Boolean filter){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM Employee ";

        if(filter){
            String condition="";

            if(txtDeptID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" dept_id LIKE '"+txtDeptID.getText()+"%'";
            }
            if(txtFName.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" fname LIKE '"+txtFName.getText()+"%'";
            }
            if(txtLName.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" lname LIKE '"+txtLName.getText()+"%'";
            }
            if(txtIdentityNo.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" id_no='"+txtIdentityNo.getText()+"'";
            }
            if(cbMaritalStatus.getValue()!=null){
                condition+=condition!=""?" AND ":"";
                condition+=" marital_stat='"+cbMaritalStatus.getValue().toString().charAt(0)+"'";
            }
            if(cbGender.getValue()!=null){
                condition+=condition!=""?" AND ":"";
                condition+=" gender='"+cbGender.getValue().toString().charAt(0)+"'";
            }
            if(txtBirthDay.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" bdate='"+txtBirthDay.getText()+"%'";
            }
            if(txtStartDay.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" start_date='"+txtStartDay.getText()+"%'";
            }
            if(txtAddress.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" address LIKE '%"+txtAddress.getText()+"%'";
            }
            if(txtPhone.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" phone_num='"+txtPhone.getText()+"'";
            }
            if(txtMail.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" email='"+txtMail.getText()+"'";
            }
            if(txtTitle.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" title='"+txtTitle.getText()+"'";
            }
            if(condition!="")
                query += "WHERE "+condition;
        }

        Statement st;
        ResultSet rs;

        try {
            st= conn.createStatement();
            rs=st.executeQuery(query);
            Employee employee;
            while (rs.next()){

                employee = new Employee(
                        rs.getInt("dept_id"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("id_no"),
                        rs.getString("marital_stat"),
                        rs.getString("gender"),
                        rs.getDate("birth_date"),
                        rs.getDate("start_date"),
                        rs.getDouble("salary"),
                        rs.getString("address"),
                        rs.getString("phone_num"),
                        rs.getString("email"),
                        rs.getString("title"));
                employees.add(employee);
            }
            return  employees;
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

    private class EditButtonCell extends TableCell<Employee, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Employee employee = (Employee) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    identityNo=employee.getIdNo();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(employee);
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

    private class DeleteButtonCell extends TableCell<Employee, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Employee employee = (Employee) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + employee.getFname()+" "+employee.getLname() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(employee.getIdNo());
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
