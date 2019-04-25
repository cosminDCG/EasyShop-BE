package EasyShop.dto;

public class PromoDTO {

    private int id;
    private int userId;
    private String promoCode;
    private int promoPercent;
    private String state;

    public PromoDTO() {
    }

    public PromoDTO(int id, int userId, String promoCode, int promoPercent, String state) {
        this.id = id;
        this.userId = userId;
        this.promoCode = promoCode;
        this.promoPercent = promoPercent;
        this.state = state;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public int getPromoPercent() {
        return promoPercent;
    }

    public void setPromoPercent(int promoPercent) {
        this.promoPercent = promoPercent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
