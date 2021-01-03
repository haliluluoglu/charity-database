package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private  String activeTab;
    @FXML
    Button btnLoadNeedy = new Button();

    @FXML
    Button btnLoadDonator = new Button();

    @FXML
    Button btnLoadMember = new Button();

    @FXML
    Button btnLoadEmployee = new Button();

    @FXML
    Button btnLoadDepartment = new Button();

    @FXML
    Button btnLoadInventory = new Button();

    @FXML
    Button btnLoadAccounting = new Button();

    @FXML
    Button btnShowHomePage = new Button();

    @FXML
    Pane paneMain;

    @FXML
    GridPane gpNavigation=new GridPane();

    @FXML
    TabPane paneHome=new TabPane();

    @FXML
    Label lblNeedy =new Label();

    @FXML
    Label lblDonator =new Label();

    @FXML
    Label lblMember =new Label();

    @FXML
    Label lblDepartment =new Label();

    @FXML
    Label lblEmployee =new Label();

    @FXML
    TextField txtIdNo=new TextField();

    @FXML
    Button btnSearch=new Button();

    @FXML
    TableView<ContactInfo> tblSearchResult;
    @FXML
    public TableColumn<ContactInfo,String> colFirstName;
    @FXML
    public TableColumn<ContactInfo,String> colLastName;
    @FXML
    public TableColumn<ContactInfo,String> colGenre;
    @FXML
    public TableColumn<ContactInfo,Integer> colAddress;
    @FXML
    public TableColumn<ContactInfo,Boolean> colPhoneNumber;
    @FXML
    public TableColumn<Inventory,Boolean> colEmail;

    @FXML
    TableView<AllByRelation> tblAll;
    @FXML
    public TableColumn<ContactInfo,String> colAllFirstName;
    @FXML
    public TableColumn<ContactInfo,String> colAllLastName;
    @FXML
    public TableColumn<ContactInfo,String> colAllGenre;

    @FXML
    Tab tabPaneStatistic= new Tab();

    @FXML
    Tab tabPaneQuick= new Tab();

    @FXML
    Tab tabPaneAll= new Tab();

    @FXML
    CheckBox cbNeedy=new CheckBox();
    @FXML
    CheckBox cbDonator=new CheckBox();
    @FXML
    CheckBox cbMember=new CheckBox();
    @FXML
    CheckBox cbEmployee=new CheckBox();

    @FXML
    Button btnLoadAll=new Button();

    @Override
    public  void  initialize(URL location, ResourceBundle resources){
        loadStatistics();

        paneHome.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            switch (newTab.getId()) {
                case "tabPaneStatistic": loadStatistics(); break;
                case "tabPaneQuick": txtIdNo.setText(""); tblSearchResult.setItems(null);  break;
                case "tabPaneAll":
                    cbEmployee.setSelected(false);
                    cbDonator.setSelected(false);
                    cbMember.setSelected(false);
                    cbNeedy.setSelected(false);
                    tblAll.setItems(null);
                    break;
            }
        });
    }
    public void showHomePage(ActionEvent event){
        paneMain.setVisible(false);
        paneHome.setVisible(true);
        loadStatistics();
        deactivateTabClass();
        activeTab="";
    }
    public void showSearchResult(ActionEvent event){
        loadTable();
    }
    public void loadAllAction(ActionEvent event) throws Exception {
        if(activeTab!=null){
            deactivateTabClass();
            clearPane();
        }
        if(!paneMain.isVisible()){
            paneMain.setVisible(true);
            paneHome.setVisible(false);
        }
        switch (((Button)event.getSource()).getId()){
            case "btnLoadNeedy": loadNeedy(); break;
            case "btnLoadDonator": loadDonator(); break;
            case "btnLoadMember": loadMember(); break;
            case "btnLoadEmployee": loadEmployee(); break;
            case "btnLoadDepartment": loadDepartment(); break;
            case "btnLoadInventory": loadInventory(); break;
            case "btnLoadAccounting": loadAccounting(); break;
        }
        activateTabClass();
    }

    private  void clearPane() throws  Exception {
        paneMain.getChildren().remove(0);
    }

    private void loadNeedy() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("needies.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="needy";
    }
    private void loadDonator() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("donators.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="donator";
    }
    private void loadMember() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("members.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="member";

    }
    private void loadEmployee() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("employee.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="employee";

    }
    private void loadDepartment() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("department.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="department";

    }
    private void loadInventory() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("inventory.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="inventory";

    }
    private void loadAccounting() throws Exception {
        Pane newLoaded= FXMLLoader.load(getClass().getResource("accounting.fxml"));
        paneMain.getChildren().add(newLoaded);
        activeTab="account";

    }

    private void activateTabClass(){
        switch (activeTab){
            case "needy":
                btnLoadNeedy.getStyleClass().add("main-btn-active");
                break;
            case "donator":
                btnLoadDonator.getStyleClass().add("main-btn-active");
                break;
            case "member":
                btnLoadMember.getStyleClass().add("main-btn-active");
                break;
            case "employee":
                btnLoadEmployee.getStyleClass().add("main-btn-active");
                break;
            case "department":
                btnLoadDepartment.getStyleClass().add("main-btn-active");
                break;
            case "inventory":
                btnLoadInventory.getStyleClass().add("main-btn-active");
                break;
            case "account":
                btnLoadAccounting.getStyleClass().add("main-btn-active");
                break;
        }
    }
    private  void deactivateTabClass(){
        switch (activeTab){
            case "needy":btnLoadNeedy.getStyleClass().remove("main-btn-active"); break;
            case "donator":btnLoadDonator.getStyleClass().remove("main-btn-active"); break;
            case "member":btnLoadMember.getStyleClass().remove("main-btn-active"); break;
            case "employee":btnLoadEmployee.getStyleClass().remove("main-btn-active"); break;
            case "department":btnLoadDepartment.getStyleClass().remove("main-btn-active"); break;
            case "inventory":btnLoadInventory.getStyleClass().remove("main-btn-active"); break;
            case "account":btnLoadAccounting.getStyleClass().remove("main-btn-active"); break;
        }

    }

    private void loadStatistics(){
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM charityStat";


        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);

            while (rs.next()){
                lblNeedy.setText(rs.getString("nofneedies"));
                lblDonator.setText(rs.getString("nofdonators"));
                lblMember.setText(rs.getString("nofmembers"));
                lblDepartment.setText(rs.getString("nofdepartments"));
                lblEmployee.setText(rs.getString("nofemployees"));
            }
        }
        catch (Exception ex){

        }
    }

    private void loadTable(){
        if(txtIdNo.getText().length()>0){
            colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("mail"));

            tblSearchResult.setItems(getContactList(txtIdNo.getText()));
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter an identity number!", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    private ObservableList<ContactInfo> getContactList(String idNo){
        ObservableList<ContactInfo> contactInfos = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String query= "SELECT * FROM contact('"+idNo+"')";

        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            ContactInfo contactInfo;
            while (rs.next()){

                contactInfo = new ContactInfo(
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("genre"),
                        rs.getString("address"),
                        rs.getString("phone_num"),
                        rs.getString("email"));
                contactInfos.add(contactInfo);
            }
            return  contactInfos;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }

    public void loadAllTable(ActionEvent event){

        colAllGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAllFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colAllLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tblAll.setItems(getAllList());
    }

    private ObservableList<AllByRelation> getAllList(){
        ObservableList<AllByRelation> allByRelations = FXCollections.observableArrayList();
        Connection conn= new DatabaseConnection().getConnection();

        String condition="";
        if(cbNeedy.isSelected()){
            condition+=condition!=""?" OR ":"";
            condition+=" relation_type= 'needy'";
        }
        if(cbDonator.isSelected()){
            condition+=condition!=""?" OR ":"";
            condition+=" relation_type= 'donators'";
        }
        if(cbEmployee.isSelected()){
            condition+=condition!=""?" OR ":"";
            condition+=" relation_type='employee'";
        }
        if(cbMember.isSelected()){
            condition+=condition!=""?" OR ":"";
            condition+=" relation_type='members'";
        }
        if(condition!="")
            condition = "WHERE "+condition;
        condition+= "ORDER BY relation_type, fname, lname";
        String query= "SELECT * FROM all_relations " + condition;

        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            AllByRelation allByRelation;
            while (rs.next()){
                allByRelation = new AllByRelation(
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("relation_type"));
                allByRelations.add(allByRelation);
            }
            return  allByRelations;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }

}
