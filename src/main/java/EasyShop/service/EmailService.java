package EasyShop.service;

import EasyShop.dto.UserDTO;

public interface EmailService {

    void sendSimpleMessage(UserDTO userDTO);
}
