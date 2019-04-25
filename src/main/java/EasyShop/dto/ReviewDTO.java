package EasyShop.dto;

import java.util.List;

public class ReviewDTO {

    private int id;
    private int productId;
    private int userId;
    private String firstName;
    private String lastName;
    private String photo;
    private String comment;
    private int review;
    private int replyTo;
    private String data;
    private List<ReviewDTO> replies;

    public ReviewDTO() {
    }

    public ReviewDTO(int id, int productId, int userId, String firstName, String lastName, String photo, String comment, int review) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.comment = comment;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(int replyTo) {
        this.replyTo = replyTo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ReviewDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<ReviewDTO> reviewDTOS) {
        this.replies = reviewDTOS;
    }
}
