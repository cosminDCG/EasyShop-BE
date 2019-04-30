package EasyShop.service.impl;

import EasyShop.dao.ItemDAO;
import EasyShop.dto.ItemDTO;
import EasyShop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    public Boolean insertItem(ItemDTO itemDTO) {

        itemDAO.insertItem(itemDTO);
        return true;
    }

    public List<ItemDTO> getAllItems() {
        return itemDAO.getAllItems();
    }

    public List<String> getAllCategories(){
        return itemDAO.getAllCategories();
    }

    public ItemDTO getCheapestChoice(String criteria) {
        return itemDAO.getCheapestChoice(criteria);
    }
}
