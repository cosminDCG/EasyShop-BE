package EasyShop.service;

import EasyShop.dto.ChatDTO;
import EasyShop.dto.ChatListDTO;

import java.util.List;

public interface ChatService {

    void insertMessage(ChatDTO chatDTO);

    List<ChatDTO> getConversation(int from_user, int to_user);

    List<ChatListDTO> getChatHistory(int user_id);

}
