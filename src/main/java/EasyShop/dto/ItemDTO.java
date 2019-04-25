package EasyShop.dto;

import java.util.List;

public class ItemDTO {

    private int id;
    private String name;
    private String description;
    private String category;
    private String price;
    private String shop;
    private String photo;
    private int quantity;
    private int cartId;
    private float review;
    private int reviewers;
    private List<ItemPropertiesDTO> properties;

    public ItemDTO () {}

    public ItemDTO(int id, String name, String description, String price, String shop, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.shop = shop;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public int getReviewers() {
        return reviewers;
    }

    public void setReviewers(int reviewers) {
        this.reviewers = reviewers;
    }

    public List<ItemPropertiesDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<ItemPropertiesDTO> properties) {
        this.properties = properties;
    }
}
