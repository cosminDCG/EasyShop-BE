package EasyShop.service;

import EasyShop.dto.BanDTO;

public interface BanService {

    Boolean addBan(BanDTO banDTO);

    BanDTO getActiveBanByUserId(int user_id);

    void updateBanDate(int ban_id, int days, String reason);
}
