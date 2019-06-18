package EasyShop.dto;

import java.util.List;

public class ChatListDTO {

   private int id;
   private UserDTO user;
   private List<ChatDTO> conversation;
   private String lastMessage;

    public ChatListDTO() {
    }

    public ChatListDTO(int id, UserDTO user, String last_message) {
        this.id = id;
        this.user = user;
        this.lastMessage = last_message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public List<ChatDTO> getConversation() {
        return conversation;
    }

    public void setConversation(List<ChatDTO> conversation) {
        this.conversation = conversation;
    }
}
