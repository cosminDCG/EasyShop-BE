package EasyShop.dto;

import java.util.Date;

public class OrderDTO {

    private int id;
    private int userId;
    private String state;
    private float price;
    private String deliveryAddress;
    private String deliveryPerson;
    private String billingAddress;
    private String billingPerson;
    private String cashPay;
    private Date data;

    public OrderDTO() {
    }

    public OrderDTO(int id, int user_id, String state, float price, String deliveryAddress, String billingAddress, Date data) {
        this.id = id;
        this.userId = user_id;
        this.state = state;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getBillingPerson() {
        return billingPerson;
    }

    public void setBillingPerson(String billingPerson) {
        this.billingPerson = billingPerson;
    }

    public String getCashPay() {
        return cashPay;
    }

    public void setCashPay(String cashPay) {
        this.cashPay = cashPay;
    }
}
