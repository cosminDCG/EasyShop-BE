package EasyShop.service.impl;

import EasyShop.dao.ItemPropertiesDAO;
import EasyShop.dto.ItemPropertiesDTO;
import EasyShop.service.ItemPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPropertiesServiceImpl implements ItemPropertiesService {

    @Autowired
    ItemPropertiesDAO itemPropertiesDAO;

    public Boolean insertProperties(List<ItemPropertiesDTO> itemPropertiesDTOS){

        itemPropertiesDAO.insertProperties(itemPropertiesDTOS);
        return true;
    }

    public List<ItemPropertiesDTO> getPropertiesByProductId(int id){
        return itemPropertiesDAO.getPropertiesByProductId(id);
    }
}
