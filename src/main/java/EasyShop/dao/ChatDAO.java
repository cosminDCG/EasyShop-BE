package EasyShop.dao;

import EasyShop.dto.ChatDTO;
import EasyShop.dto.ChatListDTO;

import java.util.List;

public interface ChatDAO {

    void insertMessage(ChatDTO chatDTO);

    List<ChatDTO> getConversation(int from_user, int to_user);
}
