package EasyShop.service.impl;

import EasyShop.dao.BanDAO;
import EasyShop.dto.BanDTO;
import EasyShop.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanServiceImpl implements BanService {

    @Autowired
    private BanDAO banDAO;

    public Boolean addBan(BanDTO banDTO){
        banDAO.addBan(banDTO);
        return true;
    }

    public BanDTO getActiveBanByUserId(int user_id){
        return banDAO.getActiveBanByUserId(user_id);
    }

    public void updateBanDate(int ban_id, int days, String reason){
        banDAO.updateBanDate(ban_id, days, reason);
    }
}
