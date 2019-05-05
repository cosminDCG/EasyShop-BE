package EasyShop.service;

import EasyShop.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    Boolean insertItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    List<String> getAllCategories();

    ItemDTO getCheapestChoice(String criteria);

    List<String> getAllShops();

    ItemDTO getCheapestSinglePlace(String criteria, String place);

    Float convertPriceToFloat(String price);

    Float getTotalPriceFromList(List<ItemDTO> itemDTOList);

    int countFoundItems(List<ItemDTO> itemDTOList);

    void truncateItems();
}
