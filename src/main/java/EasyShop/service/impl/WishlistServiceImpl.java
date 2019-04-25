package EasyShop.service.impl;

import EasyShop.dao.WishlistDAO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.WishItemDTO;
import EasyShop.dto.WishlistDTO;
import EasyShop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    WishlistDAO wishlistDAO;

    public Boolean insertWishItem(WishlistDTO wishlistDTO){
        wishlistDAO.insertWishItem(wishlistDTO);
        return true;
    }

    public List<WishItemDTO> getWishItemsForUser(int user_id) {
        return wishlistDAO.getWishItemsForUser(user_id);
    }

    public Boolean deleteWishItem(int wish_id) {
        wishlistDAO.deleteWishItem(wish_id);
        return true;
    }
}
