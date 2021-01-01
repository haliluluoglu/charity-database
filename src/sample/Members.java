package sample;

import java.util.Date;

public class Members {
    private Integer membershipID;
    private String firstName;
    private String lastName;
    private String identityNo;
    private String maritalStatus;
    private String gender;
    private Date birthDate;
    private Date startDate;
    private String address;
    private String phoneNumber;
    private String mail;

    public Members(Integer membershipID, String firstName, String lastName, String identityNo, String maritalStatus, String gender, Date birthDate, Date startDate, String address, String phoneNumber, String mail) {
        this.membershipID = membershipID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNo = identityNo;
        this.maritalStatus = maritalStatus;
        this.gender = gender;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public Integer getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(Integer membershipID) {
        this.membershipID = membershipID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
