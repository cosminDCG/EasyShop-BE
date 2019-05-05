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

    public  List<String> getAllShops() {
        return itemDAO.getAllShops();
    }

    public ItemDTO getCheapestSinglePlace(String criteria, String place) {
        return itemDAO.getCheapestSinglePlace(criteria, place);
    }

    public Float convertPriceToFloat(String price) {
        
        String[] convertedPrice = price.split("Lei");
        convertedPrice[0] = convertedPrice[0].substring(0, convertedPrice[0].length() - 1);
        convertedPrice[0] = convertedPrice[0].replace(".", "");
        convertedPrice[0] = convertedPrice[0].replace(",", ".");
        return Float.parseFloat(convertedPrice[0]);
    }

    public Float getTotalPriceFromList(List<ItemDTO> itemDTOList) {
        Float sum = 0.0f;
        for (ItemDTO itemDTO : itemDTOList ){
            if(itemDTO.getPrice() != null)
                sum += convertPriceToFloat(itemDTO.getPrice());
        }

        return sum;
    }

    public int countFoundItems(List<ItemDTO> itemDTOList) {
        int count = 0;

        for(ItemDTO itemDTO : itemDTOList){
            if(itemDTO.getId() != 0)
                count ++;
        }

        return count;
    }

    public void truncateItems(){
        itemDAO.truncateItems();
    }
}
