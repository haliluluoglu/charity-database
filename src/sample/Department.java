package sample;


public class Department {
    private Integer departmentID;
    private String departmentName;
    private String managerID;

    public Department(Integer departmentID, String departmentName, String managerID) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.managerID = managerID;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
}
