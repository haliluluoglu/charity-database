package sample;

public class ContactInfo {
    private String firstName;
    private String lastName;
    private String genre;
    private String address;
    private String phoneNumber;
    private String mail;

    public ContactInfo(String firstName, String lastName, String genre, String address, String phoneNumber, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genre = genre;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
