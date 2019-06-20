package EasyShop.service.impl;

import EasyShop.dao.BlockDAO;
import EasyShop.service.BlockService;
import org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockDAO blockDAO;

    public Boolean insertBlock(String shop){
        blockDAO.insertBlock(shop);
        return true;
    }

    public Boolean deleteBlock(String shop){
        blockDAO.deleteBlock(shop);
        return true;
    }

    public List<String> getBlockedShops(){
        return blockDAO.getBlockedShops();
    }
}
