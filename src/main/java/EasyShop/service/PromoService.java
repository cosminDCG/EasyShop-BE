package EasyShop.service;

import EasyShop.dto.OrderDTO;
import EasyShop.dto.PromoDTO;

import java.util.List;

public interface PromoService {

    void setPromo(OrderDTO orderDTO);

    PromoDTO verifyPromoAndGetPercent(int userId, String promoCode);

    Boolean useCode(PromoDTO promoDTO);

    List<PromoDTO> getPromosByUserId(int user_id);

}
