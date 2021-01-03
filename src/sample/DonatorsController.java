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
import java.util.ResourceBundle;


public class DonatorsController implements Initializable{

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
    public TextField txtCName=new TextField();

    @FXML
    public TextField txtFName=new TextField();

    @FXML
    public TextField txtLName=new TextField();

    @FXML
    public TextField txtIdentityNo=new TextField();

    @FXML
    public ChoiceBox cbGender=new ChoiceBox();

    @FXML
    public TextField txtAddress=new TextField();

    @FXML
    public TextField txtPhone=new TextField();
    @FXML
    public TextField txtMail=new TextField();

    @FXML
    public TextField txtProfession=new TextField();


    //Form Elements End

    // Tableview Start
    @FXML
    private TableView<Donators> tblDonators;

    @FXML
    public TableColumn<Donators,String> colCName;
    @FXML
    public TableColumn<Donators,String> colFName;
    @FXML
    public TableColumn<Donators,String> colLName;
    @FXML
    public TableColumn<Donators,String> colIdentityNo;
    @FXML
    public TableColumn<Donators,String> colGender;
    @FXML
    public TableColumn<Donators,String> colAddress;
    @FXML
    public TableColumn<Donators,String> colPhoneNumber;
    @FXML
    public TableColumn<Donators,String> colEMail;
    @FXML
    public TableColumn<Donators,String> colProfession;
    @FXML
    public TableColumn<Donators,Boolean> colUpdate;
    @FXML
    public TableColumn<Donators,Boolean> colDelete;
    // TableView End


    @Override
    public  void  initialize(URL location, ResourceBundle resources){

        loadTable(false);
    }

    public void saveDonatorsForm(ActionEvent event)  {
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
        txtCName.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtIdentityNo.setText("");
        cbGender.setValue(null);
        txtPhone.setText("");
        txtAddress.setText("");
        txtMail.setText("");
        txtProfession.setText("");
    }

    private void loadForm(Donators donator){
        String maritalStat=null;
        String gender=null;
        switch (donator.getGender()){
            case "F": gender="Female";break;
            case "M": gender="Male";break;

        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtCName.setText(donator.getCompanyName());
        txtFName.setText(donator.getFirstName());
        txtLName.setText(donator.getLastName());
        txtIdentityNo.setText(donator.getIdentityNo());
        cbGender.setValue(gender);
        txtPhone.setText(donator.getPhoneNumber());
        txtAddress.setText(donator.getAddress());
        txtMail.setText(donator.getMail());
        txtProfession.setText(donator.getProfession());
    }

    private void loadTable(Boolean filter){
        colCName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colIdentityNo.setCellValueFactory(new PropertyValueFactory<>("identityNo"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));

        colUpdate.setSortable(false);
        colUpdate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Donators, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Donators, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colUpdate.setCellFactory( new Callback<TableColumn<Donators, Boolean>, TableCell<Donators, Boolean>>() {
            @Override
            public TableCell<Donators, Boolean> call(TableColumn<Donators, Boolean> p) {
                return new EditButtonCell();
            }
        });

        colDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Donators, Boolean>, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Donators, Boolean> features) {
                        return new SimpleBooleanProperty(features.getValue() != null);
                    }
                }
        );
        colDelete.setCellFactory( new Callback<TableColumn<Donators, Boolean>, TableCell<Donators, Boolean>>() {
            @Override
            public TableCell<Donators, Boolean> call(TableColumn<Donators, Boolean> p) {
                return new DeleteButtonCell();
            }
        });

        tblDonators.setItems(getDonatorsList(filter));

        tblDonators.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if (newSelection != null) {

            }
        });

    }

    private void insertRecord(){
        String query = "INSERT INTO Donators VALUES('"+
                txtCName.getText()+"','"+
                txtFName.getText()+"','"+
                txtLName.getText()+"','"+
                txtIdentityNo.getText()+"','"+
                cbGender.getValue().toString().charAt(0)+"','"+
                txtAddress.getText()+"','"+
                txtPhone.getText()+"','"+
                txtMail.getText()+"','"+
                txtProfession.getText()+"')";

        executeQuery(query);
        clearForm();
    }

    private void executeQuery(String query) {
        try{
            Connection conn= new DatabaseConnection().getConnection();
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        }
        catch (Exception ex){
            System.out.println("Error:" +ex.getMessage());
        }
    }

    private void updateRecord(){
        String query = "UPDATE Donators SET "+
                "comp_name='"+ txtCName.getText()+"', "+
                "fname='"+ txtFName.getText()+"', "+
                "lname='"+txtLName.getText()+"', "+
                "id_no='"+txtIdentityNo.getText()+"', "+
                "gender='"+cbGender.getValue().toString().charAt(0)+"', "+
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
        String query = "DELETE FROM Donators WHERE id_no='"+identityNo+"'";
        executeQuery(query);
    }

    private ObservableList<Donators> getDonatorsList(Boolean filter) {
        ObservableList<Donators> donators = FXCollections.observableArrayList();
        Connection conn = new DatabaseConnection().getConnection();

        String query = "SELECT * FROM donators ";

        if (filter) {
            String condition = "";

            if (txtCName.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " comp_name LIKE '" + txtCName.getText() + "%'";
            }
            if (txtFName.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " fname LIKE '" + txtFName.getText() + "%'";
            }
            if (txtLName.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " lname LIKE '" + txtLName.getText() + "%'";
            }
            if (txtIdentityNo.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " id_no='" + txtIdentityNo.getText() + "'";
            }
            if (cbGender.getValue() != null) {
                condition += condition != "" ? " AND " : "";
                condition += " gender='" + cbGender.getValue().toString().charAt(0) + "'";
            }
            if (txtAddress.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " address LIKE '%" + txtAddress.getText() + "%'";
            }
            if (txtPhone.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " phone_num='" + txtPhone.getText() + "'";
            }
            if (txtMail.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " email='" + txtMail.getText() + "'";
            }
            if (txtProfession.getText().length() > 0) {
                condition += condition != "" ? " AND " : "";
                condition += " profession='" + txtProfession.getText() + "'";
            }
            if (condition != "")
                query += "WHERE " + condition;
        }


        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Donators donator;
            while (rs.next()) {

                donator = new Donators(
                        rs.getString("comp_name"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("id_no"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone_num"),
                        rs.getString("email"),
                        rs.getString("profession"));
                donators.add(donator);
            }
            return donators;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    private class EditButtonCell extends TableCell<Donators, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Donators donator = (Donators) EditButtonCell.this.getTableView().getItems().get(EditButtonCell.this.getIndex());
                    identityNo=donator.getIdentityNo();
                    btnCancel.setVisible(true);
                    btnClear.setVisible(false);
                    loadForm(donator);
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

    private class DeleteButtonCell extends TableCell<Donators, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    Donators donator = (Donators) DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete :" + donator.getFirstName()+" "+donator.getLastName() + " ?", ButtonType.YES,   ButtonType.CANCEL);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        deleteRecord(donator.getIdentityNo());
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
