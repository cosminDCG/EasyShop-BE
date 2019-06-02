package EasyShop.dto;

import java.util.Date;

public class BillDTO {

    private int id;
    private String shop;
    private float billValue;
    private int payed;
    private String payedBy;
    private Date emittedDate;
    private Date payDate;

    public BillDTO() {
    }

    public BillDTO(int billId, String shop, float billValue, int payed, String payedBy, Date emittedDate, Date payDate) {
        this.id = billId;
        this.shop = shop;
        this.billValue = billValue;
        this.payed = payed;
        this.payedBy = payedBy;
        this.emittedDate = emittedDate;
        this.payDate = payDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int billId) {
        this.id = billId;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public float getBillValue() {
        return billValue;
    }

    public void setBillValue(float billValue) {
        this.billValue = billValue;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public String getPayedBy() {
        return payedBy;
    }

    public void setPayedBy(String payedBy) {
        this.payedBy = payedBy;
    }

    public Date getEmittedDate() {
        return emittedDate;
    }

    public void setEmittedDate(Date emitedDate) {
        this.emittedDate = emitedDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
