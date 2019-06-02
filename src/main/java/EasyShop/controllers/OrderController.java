package EasyShop.controllers;

import EasyShop.dto.CartDTO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.OrderDTO;
import EasyShop.dto.PromoDTO;
import EasyShop.service.OrderService;
import EasyShop.service.PromoService;
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
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PromoService promoService;

    @RequestMapping(value = "/order/update", method = RequestMethod.POST)
    public ResponseEntity updateOrder(@RequestBody OrderDTO orderDTO){
        
        promoService.setPromo(orderDTO);
        Boolean ok = orderService.updateOrder(orderDTO);
        
        if (ok == true)
            return  new ResponseEntity(ok, HttpStatus.OK);
        else  return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/order/cart/insert", method = RequestMethod.POST)
    public ResponseEntity insertCart(@RequestParam int user_id, @RequestParam int item_id, @RequestParam int quantity){
        Boolean ok = orderService.insertCart(user_id, item_id, quantity);
        if (ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/cart/insert/multiple", method = RequestMethod.POST)
    public ResponseEntity insertCartMultiple(@RequestParam int user_id, @RequestBody List<ItemDTO> itemDTOList){

        Boolean ok = true;
        int count = 0;
        for(ItemDTO itemDTO : itemDTOList){
            ok = orderService.insertCart(user_id, itemDTO.getId(), itemDTO.getQuantity());
            if(ok == true)
                count ++;
        }

        return new ResponseEntity(count, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/cart/items", method = RequestMethod.GET)
    public ResponseEntity getOrderItems(@RequestParam int user_id){
        List<ItemDTO> itemDTOList = orderService.getCartItems(user_id);
        return new ResponseEntity(itemDTOList, HttpStatus.OK);
    }


    @RequestMapping(value = "/order/cart/delete", method = RequestMethod.POST)
    public ResponseEntity deleteFromCart(@RequestParam int cart_id){
        Boolean ok = orderService.deleteFromCart(cart_id);
        if (ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/cart/update", method = RequestMethod.POST)
    public ResponseEntity updateCartQuantity(@RequestBody List<CartDTO> cartDTOList){
        for(CartDTO cartDTO:cartDTOList){
            Boolean ok = orderService.updateCartQuantity(cartDTO);
            if(ok == false){
                return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/price/total", method = RequestMethod.GET)
    public ResponseEntity getTotalPriceByUserId(@RequestParam int user_id){
        float totalPrice = orderService.getTotalPriceByUserId(user_id);
        return new ResponseEntity(totalPrice, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public ResponseEntity getOrdersByUserId(@RequestParam int user_id){
        List<OrderDTO> orderDTOList = orderService.getOrdersByUserId(user_id);
        if(orderDTOList.size() != 0)
            return new ResponseEntity(orderDTOList, HttpStatus.OK);
        else return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/order/items", method = RequestMethod.GET)
    public ResponseEntity getOrderItemsByOrderId(@RequestParam int order_id){
        List<ItemDTO> itemDTOList = orderService.getOrderItemsByOrderId(order_id);
        if(itemDTOList.size() != 0)
            return new ResponseEntity(itemDTOList, HttpStatus.OK);
        else return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/order/all", method = RequestMethod.GET)
    public ResponseEntity getAllOrders(){
        List<OrderDTO> orderDTOList = orderService.getAllOrders();
        return new ResponseEntity(orderDTOList, HttpStatus.OK);
    }
}
