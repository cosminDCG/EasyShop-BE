package EasyShop.dto;

public class WishlistDTO {

    private int wishId;
    private int itemId;
    private int userId;
    private String currentPrice;
    private String expectedPrice;

    public WishlistDTO() {
    }

    public WishlistDTO(int wishId, int itemId, int userId, String currentPrice, String expectedPrice) {
        this.wishId = wishId;
        this.itemId = itemId;
        this.userId = userId;
        this.currentPrice = currentPrice;
        this.expectedPrice = expectedPrice;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(String expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
