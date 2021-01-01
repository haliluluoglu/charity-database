package sample;

import java.util.Date;

public class Employee {
    private Integer deptID;
    private String fname;
    private String lname;
    private String idNo;
    private String maritalStat;
    private String gender;
    private Date birthDate;
    private Date startDate;
    private Double salary;
    private String address;
    private String phoneNum;
    private String email;
    private String title;

    public Employee(Integer deptID, String fname, String lname, String idNo, String maritalStat, String gender, Date birthDate, Date startDate, Double salary, String address, String phoneNum, String email, String title) {
        this.deptID = deptID;
        this.fname = fname;
        this.lname = lname;
        this.idNo = idNo;
        this.maritalStat = maritalStat;
        this.gender = gender;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.salary = salary;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
        this.title = title;
    }

    public Integer getDeptID() {
        return deptID;
    }

    public void setDeptID(Integer deptID) {
        this.deptID = deptID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMaritalStat() {
        return maritalStat;
    }

    public void setMaritalStat(String maritalStat) {
        this.maritalStat = maritalStat;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
