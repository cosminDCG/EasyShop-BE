package EasyShop.service.impl;

import EasyShop.dao.RepDAO;
import EasyShop.service.RepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepServiceImpl implements RepService {

    @Autowired
    RepDAO repDAO;

    public void insertRep(int user_id, String shop){
        repDAO.insertRep(user_id, shop);
    }

    public int getShopRep(String shop){
        return repDAO.getShopRep(shop);
    }

    public void deleteRep(String shop){
        repDAO.deleteRep(shop);
    }

    public String getShopByRepId(int user_id){
        return repDAO.getShopByRepId(user_id);
    }
}
