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


public class MembersController implements Initializable{

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
    public TextField txtMemID=new TextField();
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
    public TextField txtStartDate=new TextField();

    @FXML
    public TextField txtAddress=new TextField();

    @FXML
    public TextField txtPhone=new TextField();
    @FXML
    public TextField txtMail=new TextField();

    //Form Elements End

    // Tableview Start
    @FXML
    private TableView<Members> tblEmployees;
    @FXML
    public TableColumn<Members,String> colMemID;
    @FXML
    public TableColumn<Members,String> colFName;
    @FXML
    public TableColumn<Members,String> colLName;
    @FXML
    public TableColumn<Members,String> colIdentityNo;
    @FXML
    public TableColumn<Members,String> colMaritalStatus;
    @FXML
    public TableColumn<Members,String> colGender;
    @FXML
    public TableColumn<Members, Date> colBirthDate;
    @FXML
    public TableColumn<Members, Date> colStartDate;
    @FXML
    public TableColumn<Members,String> colAddress;
    @FXML
    public TableColumn<Members,String> colPhoneNumber;
    @FXML
    public TableColumn<Members,String> colEMail;
    @FXML
    public TableColumn<Members,Boolean> colUpdate;
    @FXML
    public TableColumn<Members,Boolean> colDelete;
    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        loadTable(false);
    }

    public void saveMemberForm(ActionEvent event)  {
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
        txtMemID.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtIdentityNo.setText("");
        cbMaritalStatus.setValue(null);
        cbGender.setValue(null);
        txtBirthDay.setText("");
        txtStartDate.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtMail.setText("");
    }

    private void loadForm(Members member){
        String maritalStat=null;
        String gender=null;
        switch (member.getMaritalStatus()){
            case "S": maritalStat="Single";break;
            case "M": maritalStat="Married";break;
            case "W": maritalStat="Widowed";break;
            case "D": maritalStat="Divorced";break;
        }
        switch (member.getGender()){
            case "F": gender="Female";break;
            case "M": gender="Male";break;

        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtMemID.setText(member.getMembershipID().toString());
        txtFName.setText(member.getFirstName());
        txtLName.setText(member.getLastName());
        txtIdentityNo.setText(member.getIdentityNo());
        cbMaritalStatus.setValue(maritalStat);
        cbGender.setValue(gender);
        txtBirthDay.setText(dateFormat.format(member.getBirthDate()));
        txtStartDate.setText(dateFormat.format(member.getStartDate()));
        txtPhone.setText(member.getPhoneNumber());
        txtAddress.setText(member.getAddress());
        txtMail.setText(member.getMail());

    }

    private void loadTable(Boolean filter){

        colMemID.setCellValueFactory(new PropertyValueFactory<>("membershipID"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colIdentityNo.setCellValueFactory(new PropertyValueFactory<>("identityNo"));
        colMaritalStatus.setCellValueFactory(new PropertyValueFactory<>("maritalStatus"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
//        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//        colEMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Members, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Members, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Members, Boolean>, TableCell<Members, Boolean>>() {
            @Override
            public TableCell<Members, Boolean> call(TableColumn<Members, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Members, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Members, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Members, Boolean>, TableCell<Members, Boolean>>() {
            @Override
            public TableCell<Members, Boolean> call(TableColumn<Members, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblEmployees.setItems(getMemberList(filter));

        tblEmployees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO Member VALUES('"+
                txtMemID.getText()+"','"+
                txtFName.getText()+"','"+
                txtLName.getText()+"','"+
                txtIdentityNo.getText()+"','"+
                cbMaritalStatus.getValue().toString().charAt(0)+"','"+
                cbGender.getValue().toString().charAt(0)+"','"+
                txtBirthDay.getText()+"',"+
                txtStartDate.getText()+"',"+
                txtAddress.getText()+"','"+
                txtPhone.getText()+"','"+
                txtMail.getText()+"'"+
                ")";
        executeQuery(query);
    }

    private void updateRecord(){
        String query = "UPDATE Member SET "+
                "membership_id='"+ txtMemID.getText()+"', "+
                "fname='"+ txtFName.getText()+"', "+
                "lname='"+txtLName.getText()+"', "+
                "id_no='"+txtIdentityNo.getText()+"', "+
                "marital_stat='"+cbMaritalStatus.getValue().toString().charAt(0)+"', "+
                "gender='"+cbGender.getValue().toString().charAt(0)+"', "+
                "bdate='"+txtBirthDay.getText()+"', "+
                "start_date='"+txtStartDate.getText()+"', "+
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
        String query = "DELETE FROM Member WHERE id_no='"+identityNo+"'";
        executeQuery(query);
    }

    private ObservableList<Members> getMemberList(Boolean filter){
        ObservableList<Members> members = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM Members ";

        if(filter){
            String condition="";

            if(txtMemID.getText().length()>0){
                condition+=condition!=""?" AND ":"";
                condition+=" membership_id LIKE '"+txtMemID.getText()+"%'";
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
            Members member;
            while (rs.next()){

                member = new Members(
                        rs.getInt("membership_id"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("id_no"),
                        rs.getString("marital_stat"),
                        rs.getString("gender"),
                        rs.getDate("birth_date"),
                        rs.getDate("start_date"),
                        rs.getString("address"),
                        rs.getString("phone_num"),
                        rs.getString("email"));
                members.add(member);
            }
            return  members;
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

    private class EditButtonCell extends TableCell<Members, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Members member = (Members) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    identityNo=member.getIdentityNo();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(member);
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

    private class DeleteButtonCell extends TableCell<Members, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Members member = (Members) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + member.getMembershipID()+" "+member.getFirstName() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(member.getIdentityNo());
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
