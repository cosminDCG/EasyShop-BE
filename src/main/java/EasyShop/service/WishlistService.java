package EasyShop.service;

import EasyShop.dto.ItemDTO;
import EasyShop.dto.WishItemDTO;
import EasyShop.dto.WishlistDTO;

import java.util.List;

public interface WishlistService {

    Boolean insertWishItem(WishlistDTO wishlistDTO);

    List<WishItemDTO> getWishItemsForUser(int user_id);

    Boolean deleteWishItem(int wish_id);
}
