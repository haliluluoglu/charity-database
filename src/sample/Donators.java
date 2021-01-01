package sample;

public class Donators {
    private String companyName;
    private String firstName;
    private String lastName;
    private String identityNo;
    private String gender;
    private String address;
    private String phoneNumber;
    private String mail;
    private String profession;

    public Donators(String companyName, String firstName, String lastName, String identityNo, String gender, String address, String phoneNumber, String mail, String profession) {
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNo = identityNo;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.profession = profession;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
