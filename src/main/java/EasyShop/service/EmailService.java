package EasyShop.service;

import EasyShop.dto.BanDTO;
import EasyShop.dto.PromoDTO;
import EasyShop.dto.UserDTO;

public interface EmailService {

    void sendSimpleMessage(UserDTO userDTO);

    void sendBanMessage(BanDTO banDTO);

    void sendPromoMessage(PromoDTO promoDTO);
}
