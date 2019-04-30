package EasyShop.service;

import EasyShop.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    Boolean insertItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    List<String> getAllCategories();

    ItemDTO getCheapestChoice(String criteria);
}
