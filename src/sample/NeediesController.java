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
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class NeediesController implements Initializable{

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
    public TextField txtChildAmount=new TextField();

    @FXML
    public TextField txtIncome=new TextField();

    @FXML
    public TextField txtAddress=new TextField();

    @FXML
    public TextField txtPhone=new TextField();
    @FXML
    public TextField txtMail=new TextField();

    //Form Elements End

    // Tableview Start
    @FXML
    private TableView<Needy> tblNeedies;

    @FXML
    public TableColumn<Needy,String> colFName;
    @FXML
    public TableColumn<Needy,String> colLName;
    @FXML
    public TableColumn<Needy,String> colIdentityNo;
    @FXML
    public TableColumn<Needy,String> colMaritalStatus;
    @FXML
    public TableColumn<Needy,String> colGender;
    @FXML
    public TableColumn<Needy, Date> colBirthDate;
    @FXML
    public TableColumn<Needy,Integer> colChildAmount;
    @FXML
    public TableColumn<Needy,Integer> colIncome;
    @FXML
    public TableColumn<Needy,String> colAddress;
    @FXML
    public TableColumn<Needy,String> colPhoneNumber;
    @FXML
    public TableColumn<Needy,String> colEMail;
    @FXML
    public TableColumn<Needy,Boolean> colUpdate;
    @FXML
    public TableColumn<Needy,Boolean> colDelete;
    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){
        loadTable(false);
    }

    public void saveNeedyForm(ActionEvent event)  {
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
        txtFName.setText("");
        txtLName.setText("");
        txtIdentityNo.setText("");
        cbMaritalStatus.setValue(null);
        cbGender.setValue(null);
        txtIncome.setText("");
        txtChildAmount.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtMail.setText("");
        txtBirthDay.setText("");
    }

    private void loadForm(Needy needy){
        String maritalStat=null;
        String gender=null;
        switch (needy.getMaritalStatus()){
            case "S": maritalStat="Single";break;
            case "M": maritalStat="Married";break;
            case "W": maritalStat="Widowed";break;
            case "D": maritalStat="Divorced";break;
        }
        switch (needy.getGender()){
            case "F": gender="Female";break;
            case "M": gender="Male";break;

        }
        System.out.println(maritalStat);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtFName.setText(needy.getFirstName());
        txtLName.setText(needy.getLastName());
        txtIdentityNo.setText(needy.getIdentityNo());
        cbMaritalStatus.setValue(maritalStat);
        cbGender.setValue(gender);
        txtIncome.setText(needy.getIncome().toString());
        txtChildAmount.setText(needy.getChildAmount().toString());
        txtPhone.setText(needy.getPhoneNumber());
        txtAddress.setText(needy.getAddress());
        txtMail.setText(needy.getMail());
        txtBirthDay.setText(dateFormat.format(needy.getBirthDate()));
    }

    private void loadTable(Boolean filter){

        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colIdentityNo.setCellValueFactory(new PropertyValueFactory<>("identityNo"));
        colMaritalStatus.setCellValueFactory(new PropertyValueFactory<>("maritalStatus"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colChildAmount.setCellValueFactory(new PropertyValueFactory<>("childAmount"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        /*
        *
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        */

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Needy, Boolean>, ObservableValue<Boolean>>() {
                @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Needy, Boolean> features) {
                    return new SimpleBooleanProperty(features.getValue() != null);
                }
            }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Needy, Boolean>, TableCell<Needy, Boolean>>() {
            @Override
            public TableCell<Needy, Boolean> call(TableColumn<Needy, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Needy, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Needy, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Needy, Boolean>, TableCell<Needy, Boolean>>() {
            @Override
            public TableCell<Needy, Boolean> call(TableColumn<Needy, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblNeedies.setItems(getNeedyList(filter));

        tblNeedies.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO needy VALUES('"+
                txtFName.getText()+"','"+
                txtLName.getText()+"','"+
                txtIdentityNo.getText()+"','"+
                cbMaritalStatus.getValue().toString().charAt(0)+"','"+
                cbGender.getValue().toString().charAt(0)+"','"+
                txtBirthDay.getText()+"',"+
                txtChildAmount.getText()+","+
                txtIncome.getText()+",'"+
                txtAddress.getText()+"','"+
                txtPhone.getText()+"','"+
                txtMail.getText()+"'"+
        ")";
        executeQuery(query);
    }

    private void updateRecord(){
        String query = "UPDATE needy SET "+
                "fname='"+ txtFName.getText()+"', "+
                "lname='"+txtLName.getText()+"', "+
                "id_no='"+txtIdentityNo.getText()+"', "+
                "marital_stat='"+cbMaritalStatus.getValue().toString().charAt(0)+"', "+
                "gender='"+cbGender.getValue().toString().charAt(0)+"', "+
                "bdate='"+txtBirthDay.getText()+"', "+
                "child_amount="+txtChildAmount.getText()+", "+
                "income="+txtIncome.getText()+", "+
                "address='"+txtAddress.getText()+"', "+
                "phone_num='"+txtPhone.getText()+"', "+
                "email='"+txtMail.getText()+"' "+
                " WHERE id_no='" + identityNo + "'";
        executeQuery(query);
        clearForm();
        btnCancel.setVisible(false);
        identityNo=null;
    }

    private void deleteRecord(String identityNo){
        String query = "DELETE FROM needy WHERE id_no='"+identityNo+"'";
        executeQuery(query);
    }

    private ObservableList<Needy> getNeedyList(Boolean filter){
        ObservableList<Needy> needies = FXCollections.observableArrayList();
        Connection conn= getConnection();

        String query= "SELECT * FROM needy ";

        if(filter){
            String condition="";

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
            if(txtChildAmount.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" child_amount="+txtChildAmount.getText();
            }
            if(txtIncome.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" income="+txtIncome.getText();
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
            if(condition!="")
                query += "WHERE "+condition;
        }

        Statement st;
        ResultSet rs;

        try {
            st= conn.createStatement();
            rs=st.executeQuery(query);
            Needy needy;
            while (rs.next()){

                needy = new Needy(
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("id_no"),
                        rs.getString("marital_stat"),
                        rs.getString("gender"),
                        rs.getDate("bdate"),
                        rs.getInt("child_amount"),
                        rs.getInt("income"),
                        rs.getString("address"),
                        rs.getString("phone_num"),
                        rs.getString("email"));
                needies.add(needy);
            }
            return  needies;
        }catch (Exception ex){
            System.out.println("Error:"+ex.getMessage());
            return  null;
        }
    }

    private  void executeQuery(String query){
        try{
            Connection conn= getConnection();
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            System.out.println("Error:" +ex.getMessage());
        }
    }

    private Connection getConnection(){
        Connection conn;
        try{
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/charity_Db", "postgres", "123456");
            return  conn;
        }catch (Exception ex){
            System.out.println("Err:"+ex.getMessage());
            return null;
        }

    }

    private class EditButtonCell extends TableCell<Needy, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Needy needy = (Needy) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    identityNo=needy.getIdentityNo();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(needy);
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

    private class DeleteButtonCell extends TableCell<Needy, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Needy needy = (Needy) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + needy.getFirstName()+" "+needy.getLastName() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(needy.getIdentityNo());
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
