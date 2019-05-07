package EasyShop.dao;

import EasyShop.dto.BanDTO;

public interface BanDAO {

    void addBan(BanDTO banDTO);

    BanDTO getActiveBanByUserId(int user_id);

    void updateBanDate(int ban_id, int days, String reason);
}
