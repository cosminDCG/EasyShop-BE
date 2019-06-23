package EasyShop.dao;

import EasyShop.dto.CartDTO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.OrderDTO;
import org.hibernate.criterion.Order;

import java.util.List;

public interface OrderDAO {

    void updateOrder(OrderDTO orderDTO);

    void insertCart(int user_id, int item_id, int quantity);

    List<ItemDTO> getCartItems(int user_id);

    void deleteFromCart(int cart_id);

    void updateCartQuantity(CartDTO cartDTO);

    float getTotalPriceByUserId(int userId);

    List<OrderDTO> getOrdersByUserId(int user_id);

    List<ItemDTO> getOrderItemsByOrderId(int order_id);

    int getInProgressOrderByUserId(int user_id);

    int checkIfItemExistsInCart(int item_id, int order_id);

    int getQuantityByCartId(int cart_id);

    void addQuantityToItem(int cart_id, int quantity);

    List<OrderDTO> getAllOrders();

    List<ItemDTO> getOrderItemsByOrderIdAndShop(int order_id, String shop);

    List<OrderDTO> getOrdersFromShop(String shop);

    List<OrderDTO> getLastMonthOrdersFromShop(String shop);

    OrderDTO getLastOrderByUserId(int user_id);
}
