package EasyShop.controllers;

import EasyShop.dto.ItemDTO;
import EasyShop.dto.WishItemDTO;
import EasyShop.dto.WishlistDTO;
import EasyShop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @RequestMapping(value = "/wishlist/insert", method = RequestMethod.POST)
    public ResponseEntity insertWishItem(@RequestBody WishlistDTO wishlistDTO){
        Boolean ok = wishlistService.insertWishItem(wishlistDTO);
        if (ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }

    @RequestMapping(value = "/wishlist/item", method = RequestMethod.GET)
    public ResponseEntity getWishItemsForUser(@RequestParam int user_id){
        List<WishItemDTO> itemDTOList = wishlistService.getWishItemsForUser(user_id);
        return new ResponseEntity(itemDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/wishlist/delete", method = RequestMethod.POST)
    public ResponseEntity deleteWishItem(@RequestParam int wish_id){
        Boolean ok = wishlistService.deleteWishItem(wish_id);
        if(ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
