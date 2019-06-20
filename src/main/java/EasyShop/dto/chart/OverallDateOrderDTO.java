package EasyShop.dto.chart;

import java.util.Date;

public class OverallDateOrderDTO {

    private String shop;
    private Float stats;
    private Float stats2;
    private Date date;
    private int quantity;

    public OverallDateOrderDTO() {
    }

    public OverallDateOrderDTO(String shop, Float stats, Date date, int quantity) {
        this.shop = shop;
        this.stats = stats;
        this.date = date;
        this.quantity = quantity;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Float getStats() {
        return stats;
    }

    public void setStats(Float stats) {
        this.stats = stats;
    }

    public Float getStats2() {
        return stats2;
    }

    public void setStats2(Float stats2) {
        this.stats2 = stats2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
