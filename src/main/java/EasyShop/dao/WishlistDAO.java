package EasyShop.dao;

import EasyShop.dto.ItemDTO;
import EasyShop.dto.WishItemDTO;
import EasyShop.dto.WishlistDTO;

import java.util.List;

public interface WishlistDAO {

    void insertWishItem(WishlistDTO wishlistDTO);

    List<WishItemDTO> getWishItemsForUser(int user_id);

    void deleteWishItem(int wish_id);
}
