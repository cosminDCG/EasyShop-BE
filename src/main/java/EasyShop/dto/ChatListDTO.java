package EasyShop.dto;

public class ChatListDTO {

    private int id;
    private String photo;
    private String lastMessage;

    public ChatListDTO() {
    }

    public ChatListDTO(int id, String photo, String lastMessage) {
        this.id = id;
        this.photo = photo;
        this.lastMessage = lastMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
