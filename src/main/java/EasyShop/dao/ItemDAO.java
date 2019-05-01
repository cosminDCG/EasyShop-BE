package EasyShop.dao;

import EasyShop.dto.ItemDTO;

import java.util.List;

public interface ItemDAO {

    void insertItem(ItemDTO itemDTO);

    int getItemCount();

    List<ItemDTO> getAllItems();

    List<String> getAllCategories();

    int getReviewSum(int item_id);

    int getReviewCount(int item_id);

    ItemDTO getCheapestChoice(String criteria);

    List<String> getAllShops();

    ItemDTO getCheapestSinglePlace(String criteria, String place);
}
