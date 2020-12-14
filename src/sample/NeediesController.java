package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;


public class NeediesController implements Initializable{

    @FXML
    public Button btnSave= new Button();

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
    // TableView End



    @Override
    public  void  initialize(URL location, ResourceBundle resources){
        loadTable();
    }

    public void saveNeedyForm(ActionEvent event)  {
        insertRecord();
        loadTable();
    }

    private void loadTable(){

        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colIdentityNo.setCellValueFactory(new PropertyValueFactory<>("identityNo"));
        colMaritalStatus.setCellValueFactory(new PropertyValueFactory<>("maritalStatus"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colChildAmount.setCellValueFactory(new PropertyValueFactory<>("childAmount"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

        tblNeedies.setItems(getNeedyList());

        tblNeedies.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if (newSelection != null) {
                System.out.println(newSelection.getIdentityNo());
                 //tblNeedies.getSelectionModel().clearSelection();
            }
        });
    }

    private  void  insertRecord(){
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

    private ObservableList<Needy> getNeedyList(){
        ObservableList<Needy> needies= FXCollections.observableArrayList();
        Connection conn= getConnection();
        String query= "SELECT * FROM needy";
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

}
