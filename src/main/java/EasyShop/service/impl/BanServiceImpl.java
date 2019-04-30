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
}
