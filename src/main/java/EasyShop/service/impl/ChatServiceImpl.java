package EasyShop.service.impl;

import EasyShop.dao.ChatDAO;
import EasyShop.dto.ChatDTO;
import EasyShop.dto.ChatListDTO;
import EasyShop.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDAO chatDAO;

    public void insertMessage(ChatDTO chatDTO){
        chatDAO.insertMessage(chatDTO);
    }

    public List<ChatDTO> getConversation(int from_user, int to_user){
        return chatDAO.getConversation(from_user, to_user);
    }

    public List<ChatListDTO> getChatHistory(int user_id){
        return chatDAO.getChatHistory(user_id);
    }
}

