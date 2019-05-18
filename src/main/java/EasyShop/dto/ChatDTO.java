package EasyShop.dto;

import java.util.Date;

public class ChatDTO {

    private int id;
    private int fromUser;
    private int toUser;
    String text;
    Date sendDate;

    public ChatDTO() {
    }

    public ChatDTO(int id, int fromUser, int toUser, String text, Date sendDate) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
        this.sendDate = sendDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
