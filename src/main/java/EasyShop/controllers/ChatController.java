package EasyShop.controllers;

import EasyShop.dto.ChatDTO;
import EasyShop.dto.ChatListDTO;
import EasyShop.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ChatService chatService;

    @Autowired
    ChatController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(ChatDTO chatDTO) throws ParseException {
        chatService.insertMessage(chatDTO);
        Date date = new Date();
        chatDTO.setSendDate(dateFormat.parse(dateFormat.format(date)));
        this.template.convertAndSend("/chat", chatDTO);
    }

    @RequestMapping(value = "/conversation", method = RequestMethod.GET)
    public ResponseEntity getConversation(@RequestParam int from_user, @RequestParam int to_user){

        List<ChatDTO> conversation = chatService.getConversation(from_user, to_user);
        return new ResponseEntity(conversation, HttpStatus.OK);
    }

    @RequestMapping(value = "/chat/history", method = RequestMethod.GET)
    public ResponseEntity getChatHistory(@RequestParam int id){
        List<ChatListDTO> chatHistory = chatService.getChatHistory(id);
        return new ResponseEntity(chatHistory, HttpStatus.OK);
    }
}
