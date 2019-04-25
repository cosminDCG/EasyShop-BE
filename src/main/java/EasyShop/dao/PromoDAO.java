package EasyShop.dao;

import EasyShop.dto.PromoDTO;

import java.util.List;

public interface PromoDAO {

    void insertPromo(PromoDTO promoDTO);

    PromoDTO verifyPromoAndGetPercent(int userId, String promoCode);

    void useCode(PromoDTO promoDTO);

    List<PromoDTO> getPromosByUserId(int user_id);
}
