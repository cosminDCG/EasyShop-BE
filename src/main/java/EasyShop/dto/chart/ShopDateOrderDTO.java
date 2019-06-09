package EasyShop.dto.chart;

import java.util.Date;

public class ShopDateOrderDTO {

    private Date date;
    private Float stats;
    private int quantity;

    public ShopDateOrderDTO() {
    }

    public ShopDateOrderDTO(Date date, Float stats) {
        this.date = date;
        this.stats = stats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getStats() {
        return stats;
    }

    public void setStats(Float stats) {
        this.stats = stats;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
