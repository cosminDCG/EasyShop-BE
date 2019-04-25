package EasyShop.dto;

public class CartDTO {

    private int cartId;
    private int orderId;
    private int itemId;
    private int quantity;

    public CartDTO() {
    }

    public CartDTO(int cartId, int orderId, int itemId, int quantity) {
        this.cartId = cartId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
