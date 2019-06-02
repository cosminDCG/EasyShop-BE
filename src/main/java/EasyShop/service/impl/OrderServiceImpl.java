package EasyShop.service.impl;

import EasyShop.dao.OrderDAO;
import EasyShop.dto.CartDTO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.OrderDTO;
import EasyShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public Boolean updateOrder(OrderDTO orderDTO){

        orderDAO.updateOrder(orderDTO);
        return true;

    }

    public Boolean insertCart(int user_id, int item_id, int quantity){

        int order_id = orderDAO.getInProgressOrderByUserId(user_id);
        int check = orderDAO.checkIfItemExistsInCart(item_id, order_id);
        orderDAO.insertCart(user_id, item_id, quantity);

        if(check != 0)
            return false;
        
        return true;

    }

    public List<ItemDTO> getCartItems(int user_id){
        return orderDAO.getCartItems(user_id);
    }

    public Boolean deleteFromCart(int cart_id){
        orderDAO.deleteFromCart(cart_id);
        return true;
    }

    public Boolean updateCartQuantity(CartDTO cartDTO){
        orderDAO.updateCartQuantity(cartDTO);
        return true;
    }

    public float getTotalPriceByUserId(int userId){
        return orderDAO.getTotalPriceByUserId(userId);
    }

    public List<OrderDTO> getOrdersByUserId(int user_id){
        return orderDAO.getOrdersByUserId(user_id);
    }

    public List<ItemDTO> getOrderItemsByOrderId(int order_id){
        return orderDAO.getOrderItemsByOrderId(order_id);
    }

    public int checkIfItemExistsInCart(int item_id, int order_id) {
        return orderDAO.checkIfItemExistsInCart(item_id, order_id);
    }

    public List<OrderDTO> getAllOrders(){
        return orderDAO.getAllOrders();
    }
}
