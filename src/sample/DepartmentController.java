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
import javafx.util.StringConverter;

import javax.swing.plaf.nimbus.State;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    public Button btnShowEmployees= new Button();

    @FXML
    public ComboBox<Department> cbDepartment= new ComboBox<Department>();


    // Form Elements Start
    @FXML
    public TextField txtDeptID=new TextField();

    @FXML
    public TextField txtDeptName=new TextField();

    @FXML
    public ComboBox<Employee> cbManager=new ComboBox<Employee>();

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

    @FXML
    private TableView<EmpByDepartmetment> tblEmployee;

    @FXML
    public TableColumn<EmpByDepartmetment,String> colEmployee;


    @Override
    public  void  initialize(URL location, ResourceBundle resources){
        loadTable(false);
        loadManager();
        loadDepartment();
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

    public void loadEmpList(ActionEvent event)  {
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("line"));

        tblEmployee.setItems(getEmpList());
    }

    private void clearForm(){
        txtDeptID.setText("");
        txtDeptName.setText("");
        cbManager.setValue(null);
    }

    private void loadForm(Department department){
        txtDeptID.setText(department.getDepartmentID().toString());
        txtDeptName.setText(department.getDepartmentName());
        cbManager.setValue(getManager(department.getManagerID()));

    }

    private  void loadManager() {

        ObservableList<Employee> employees = FXCollections.observableArrayList();
        Connection conn = new DatabaseConnection().getConnection();

        String query = "SELECT * FROM Employee";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Employee employee = new Employee(
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
            cbManager.setItems(employees);

            cbManager.setConverter(new StringConverter<Employee>() {

                @Override
                public String toString(Employee employee) {
                    return employee!=null? employee.getFname()+" "+employee.getLname()+"-"+employee.getIdNo().toString() :null;
                }

                @Override
                public Employee fromString(String string) {
                    System.out.println(string);
                    return cbManager.getItems().stream().filter(ap ->
                            (ap.getFname()+" "+ ap.getLname()+"-"+ap.getIdNo().toString()).equals(string)).findFirst().orElse(null);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private Employee getManager(String managerId){
        Connection conn= new DatabaseConnection().getConnection();
        Employee employee=null;
        String query= "SELECT * FROM Employee WHERE id_no='"+managerId+"'";
        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            boolean  deptAssigned=false;
            while (rs.next() && deptAssigned==false){

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
                deptAssigned=true;
            }
            return employee;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private  void loadDepartment() {
        ObservableList<Department> departments = FXCollections.observableArrayList();
        Connection conn = new DatabaseConnection().getConnection();

        String query = "SELECT * FROM department";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Department department = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("dept_mgr_id"));
                departments.add(department);
            }
            cbDepartment.setItems(departments);

            cbDepartment.setConverter(new StringConverter<Department>() {

                @Override
                public String toString(Department department) {
                    return department!=null? department.getDepartmentName():null;
                }

                @Override
                public Department fromString(String string) {
                    return cbDepartment.getItems().stream().filter(ap ->
                            ap.getDepartmentName().equals(string)).findFirst().orElse(null);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadTable(Boolean filter){
        colDeptID.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        colDeptName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        colMgrID.setCellValueFactory(new PropertyValueFactory<>("managerID"));

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
        String query = "INSERT INTO  Department (dept_name, dept_mgr_id) VALUES('"+
                txtDeptName.getText()+"','"+
                cbManager.getValue().getIdNo()+
                "')";
        executeQuery(query);

    }

    private void updateRecord(){
        String query = "UPDATE Department SET "+
                "dept_name='"+txtDeptName.getText()+"', "+
                "dept_mgr_id='"+cbManager.getValue().getIdNo()+"', "+
                " WHERE dept_id=" + deptNo;
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
                condition+=" dept_id LIKE '"+txtDeptID.getText()+"%'";
            }
            if(txtDeptID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" dept_name LIKE '"+txtDeptName.getText()+"%'";
            }
            if(cbManager.getValue()!=null){
                condition+=condition!=""?" AND ":"";
                condition+=" dept_mgr_id='"+cbManager.getValue().getIdNo()+"'";
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


    private ObservableList<EmpByDepartmetment> getEmpList(){
        ObservableList<EmpByDepartmetment> lines = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT cursorc("+cbDepartment.getValue().getDepartmentID()+")";

        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            SQLWarning warning = st.getWarnings();
            while (warning!=null){
                lines.add(new EmpByDepartmetment(warning.getMessage()));
                warning=warning.getNextWarning();
            }
            return  lines;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //ex.printStackTrace();

            return  null;
        }
    }
}
