package EasyShop.service;

import EasyShop.dto.BanDTO;
import EasyShop.dto.OrderDTO;
import EasyShop.dto.PromoDTO;
import EasyShop.dto.UserDTO;

import javax.mail.MessagingException;

public interface EmailService {

    void sendSimpleMessage(UserDTO userDTO);

    void sendBanMessage(BanDTO banDTO);

    void sendPromoMessage(PromoDTO promoDTO);

    void sendRecoveryMessage(UserDTO userDTO, String newPass);

    void sendOrderEmail(OrderDTO orderDTO) throws MessagingException;
}
