package sample;


import java.util.Date;

public class Accounting {
    private Integer balanceID;
    private String balanceType;
    private String description;
    private Date balanceDate;
    private Double amount;
    private Integer departmentID;

    public Accounting(Integer balanceID, String balanceType, String description, Date balanceDate, Double amount, Integer departmentID) {
        this.balanceID = balanceID;
        this.balanceType = balanceType;
        this.description = description;
        this.balanceDate = balanceDate;
        this.amount = amount;
        this.departmentID = departmentID;
    }

    public Integer getBalanceID() {
        return balanceID;
    }

    public void setBalanceID(Integer balanceID) {
        this.balanceID = balanceID;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }
}
