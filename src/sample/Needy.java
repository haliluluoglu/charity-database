package sample;

import java.util.Date;

public class Needy {
    private String firstName;
    private String lastName;
    private String identityNo;
    private String maritalStatus;
    private String gender;
    private Date birthDate;
    private Integer childAmount;
    private Integer income;
    private String address;
    private String phoneNumber;
    private String mail;

    public Needy(String firstName, String lastName, String identityNo, String maritalStatus, String gender, Date birthDate, Integer childAmount, Integer income, String address, String phoneNumber, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNo = identityNo;
        this.maritalStatus = maritalStatus;
        this.gender = gender;
        this.birthDate = birthDate;
        this.childAmount = childAmount;
        this.income = income;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Integer getChildAmount() {
        return childAmount;
    }

    public Integer getIncome() {
        return income;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setChildAmount(Integer childAmount) {
        this.childAmount = childAmount;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
