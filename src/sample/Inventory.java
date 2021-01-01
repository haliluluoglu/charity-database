package sample;


public class Inventory {
    private Integer inventoryID;
    private String inventoryType;
    private String description;
    private Integer amount;

    public Inventory(Integer inventoryID, String inventoryType, String description, Integer amount) {
        this.inventoryID = inventoryID;
        this.inventoryType = inventoryType;
        this.description = description;
        this.amount = amount;
    }

    public Integer getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
