package EasyShop.service;

import EasyShop.dto.CartDTO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    Boolean updateOrder(OrderDTO orderDTO);

    Boolean insertCart(int user_id, int item_id, int quantity);

    List<ItemDTO> getCartItems(int user_id);

    Boolean deleteFromCart(int cart_id);

    Boolean updateCartQuantity(CartDTO cartDTO);

    float getTotalPriceByUserId(int userId);

    List<OrderDTO> getOrdersByUserId(int user_id);

    List<ItemDTO> getOrderItemsByOrderId(int order_id);

    int checkIfItemExistsInCart(int item_id, int order_id);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getOrdersFromShop(String shop);

    List<OrderDTO> getLastMonthOrdersFromShop(String shop);
}
