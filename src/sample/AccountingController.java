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
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.EventObject;
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
    public Button btnShowResult = new Button();

    @FXML
    public TextField txtBalanceID = new TextField();

    @FXML
    public ChoiceBox cbBalanceType = new ChoiceBox();

    @FXML
    public TextField txtDescription = new TextField();

    @FXML
    public DatePicker txtBalanceDate = new DatePicker();

    @FXML
    public TextField txtAmount = new TextField();

    @FXML
    public ComboBox<Department> cbDepartment = new ComboBox<Department>();

    @FXML
    public TextField txtByYear= new TextField();

    @FXML
    public ChoiceBox cbByType = new ChoiceBox();

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

    @FXML
    private TableView<AccountingByYear> tblCalculations;
    @FXML
    public TableColumn<AccountingByYear,String> colByMonth;
    @FXML
    public TableColumn<AccountingByYear,String> colByTotal;

    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        loadTable(false);
        loadDepartment();
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

    public void loadCalculations(ActionEvent event)  {
        if(cbByType.getValue()!=null && txtByYear.getText().length()>0){
            colByMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            colByTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblCalculations.setItems(getTotalByMonth());
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "You must specify type and  year!",  ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    public void cancelUpdate(ActionEvent event)  {
        clearForm();
        balanceID =null;
        btnCancel.setVisible(false);
    }

    private void clearForm(){
        txtBalanceID.setText("");
        cbBalanceType.setValue(null);
        txtDescription.setText("");
        txtBalanceDate.setValue(null);
        txtAmount.setText("");
        cbDepartment.setValue(null);
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
    private Department getDepartment(int deptId){
        Connection conn= new DatabaseConnection().getConnection();
        Department department=null;
        String query= "SELECT * FROM department WHERE dept_id="+deptId;
        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            boolean  deptAssigned=false;
            while (rs.next() && deptAssigned==false){

                department = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("dept_mgr_id"));
                deptAssigned=true;
            }
            return  department;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private void loadForm(Accounting accounting){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtBalanceID.setText(accounting.getBalanceID().toString());
        cbBalanceType.setValue(accounting.getBalanceType());
        txtDescription.setText(accounting.getDescription());
        txtBalanceDate.setValue(LocalDate.parse(dateFormat.format(accounting.getBalanceDate())));
        txtAmount.setText(accounting.getAmount().toString());
        cbDepartment.setValue(getDepartment(accounting.getDepartmentID()));
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

        String query = "INSERT INTO balance " +
                "(balance_type, balance_date, amount, dept_id, description) VALUES('"+
                cbBalanceType.getValue()+"','"+
                txtBalanceDate.getValue()+"',"+
                txtAmount.getText()+","+
                cbDepartment.getValue().getDepartmentID()+",'"+
                txtDescription.getText()+"')";
        executeQuery(query);

    }

    private void updateRecord(){
        String query = "UPDATE Balance SET "+
                "balance_type='"+ cbBalanceType.getValue()+"', "+
                "description='"+ txtDescription.getText()+"', "+
                "balance_date='"+ txtBalanceDate.getValue()+"', "+
                "amount="+ txtAmount.getText()+", "+
                "dept_id="+ cbDepartment.getValue().getDepartmentID()+
                " WHERE balance_id=" + balanceID;
        executeQuery(query);
        clearForm();
        btnCancel.setVisible(false);
        balanceID =null;
    }

    private void deleteRecord(String balanceID){
        String query = "DELETE FROM Balance WHERE balance_id="+balanceID;
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
                condition+=" balance_id='"+ txtBalanceID.getText()+"'";
            }
            if(cbBalanceType.getValue()!=null){
                condition+=condition!=""?" AND ":"";
                condition+=" balance_type= '"+ cbBalanceType.getValue().toString()+"'";
            }
            if(txtBalanceDate.getValue()!=null){
                condition+=condition!=""?" AND ":"";
                condition+=" balance_date='"+ txtBalanceDate.getValue()+"'";
            }
            if(txtDescription.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" description LIKE '"+ txtDescription.getText()+"%'";
            }
            if(txtAmount.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" income="+ txtAmount.getText();
            }
            if(cbDepartment.getValue()!=null){
                condition+=condition!=""?" AND ":"";
                condition+=" dept_id="+ cbDepartment.getValue().getDepartmentID();
            }
            if(condition!="")
                query += "WHERE "+condition;
        }

        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
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
            int result = st.executeUpdate(query);
            if(result>0){
                clearForm();
            }
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

    private ObservableList<AccountingByYear> getTotalByMonth(){
        ObservableList<AccountingByYear> accountings = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();
        String query;
        query = "SELECT EXTRACT(MONTH FROM balance_date) as month, sum(amount) as total " +
                "FROM balance WHERE balance_type='"+cbByType.getValue()+"' " +
                "GROUP BY DATE_PART('year', balance_date::date), DATE_PART('month', balance_date::date) " +
                "HAVING DATE_PART('year', balance_date::date)="+txtByYear.getText();
        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            AccountingByYear accounting;
            while (rs.next()){
                accounting = new AccountingByYear(
                        rs.getString("month"),
                        rs.getString("total"));

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

}
