package EasyShop.dao.impl;

import EasyShop.dao.OrderDAO;
import EasyShop.dto.CartDTO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcOrderDAO implements OrderDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    public void insertOrder(int user_id){
        String sqlInsert = "" +
                "INSERT INTO orders(user_id, state) VALUES( " +
                "    :user_id, " +
                "    'in progress' " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    public int getInProgressOrderByUserId(int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "    order_id " +
                "FROM orders " +
                "WHERE user_id =:user_id " +
                "AND state = 'in progress' ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int result = 0;
            while(rs.next()) {
                result = rs.getInt("order_id");
            }
            return result;

        });
    }

    @Override
    public void updateOrder(OrderDTO orderDTO){
        int order_id = getInProgressOrderByUserId(orderDTO.getUserId());
        String sqlUpdate = "" +
                "UPDATE orders " +
                "SET state = 'complete', " +
                " data = SYSDATE(), " +
                " price =:price, " +
                " delivery_address =:delivery_address, " +
                " delivery_person =:delivery_person, " +
                " billing_address =:billing_address, " +
                " billing_person =:billing_person, " +
                " cash_pay =:cash_pay " +
                "WHERE order_id = :order_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("order_id", order_id);
        namedParameters.addValue("price", orderDTO.getPrice());
        namedParameters.addValue("delivery_address", orderDTO.getDeliveryAddress());
        namedParameters.addValue("delivery_person", orderDTO.getDeliveryPerson());
        namedParameters.addValue("billing_address", orderDTO.getBillingAddress());
        namedParameters.addValue("billing_person", orderDTO.getBillingPerson());
        namedParameters.addValue("cash_pay", orderDTO.getCashPay());

        jdbcTemplate.update(sqlUpdate, namedParameters);
    }

    @Override
    public OrderDTO getLastOrderByUserId(int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM orders " +
                "WHERE user_id =:user_id " +
                "AND state = 'complete' " +
                "ORDER BY order_id desc " +
                "LIMIT 1";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            OrderDTO results = new OrderDTO();
            while(rs.next()) {
                results.setId(rs.getInt("order_id"));
                results.setUserId(rs.getInt("user_id"));
                results.setPrice(rs.getFloat("price"));
                results.setState(rs.getString("state"));
                results.setDeliveryPerson(rs.getString("delivery_person"));
                results.setDeliveryAddress(rs.getString("delivery_address"));
                results.setBillingPerson(rs.getString("billing_person"));
                results.setBillingAddress(rs.getString("billing_address"));
                results.setCashPay(rs.getString("cash_pay"));
                results.setData(rs.getDate("data"));
                results.setItems(getOrderItemsByOrderId(results.getId()));

            }
            return results;

        });
    }

    @Override
    public int checkIfItemExistsInCart(int item_id, int order_id){
        String sqlSelect = "" +
                "SELECT " +
                "    cart_id " +
                "FROM cart  " +
                "WHERE item_id = :item_id " +
                "AND order_id = :order_id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("order_id", order_id);
        namedParameters.addValue("item_id", item_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int results = 0;
            while(rs.next()) {
                results = rs.getInt("cart_id");
            }
            return results;

        });

    }

    @Override
    public int getQuantityByCartId(int cart_id){
        String sqlSelect = "" +
                "SELECT " +
                "    quantity " +
                "FROM cart  " +
                "WHERE cart_id = :cart_id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cart_id", cart_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int results = 0;
            while(rs.next()) {
                results = rs.getInt("quantity");
            }
            return results;

        });
    }

    @Override
    public void addQuantityToItem(int cart_id, int quantity){
        String sqlUpdate = "" +
                "UPDATE cart " +
                "SET quantity =:quantity " +
                "WHERE cart_id = :cart_id ";

        int currentQuantity = getQuantityByCartId(cart_id);
        int updateQuantity = currentQuantity + quantity;

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cart_id", cart_id);
        namedParameters.addValue("quantity", updateQuantity);

        jdbcTemplate.update(sqlUpdate, namedParameters);
    }

    @Override
    public void insertCart(int user_id, int item_id, int quantity){

        int order = 0;
        if(getInProgressOrderByUserId(user_id) == 0){
            insertOrder(user_id);
            order = getInProgressOrderByUserId(user_id);
        } else order = getInProgressOrderByUserId(user_id);

        int existing_item_id = checkIfItemExistsInCart(item_id, order);

        if(existing_item_id != 0){
            addQuantityToItem(existing_item_id, quantity);
            return;
        }
        String sqlInsert = "" +
                "INSERT INTO cart(order_id, item_id, quantity) VALUES( " +
                "    :order_id, " +
                "    :item_id, " +
                "    :buy_quantity " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("order_id", order);
        namedParameters.addValue("item_id", item_id);
        namedParameters.addValue("buy_quantity", quantity);

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public List<ItemDTO> getCartItems(int user_id){
        int order = getInProgressOrderByUserId(user_id);
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM items i, orders o, cart c " +
                "WHERE o.order_id = c.order_id " +
                "AND c.item_id = i.item_id " +
                "AND o.user_id =:user_id " +
                "AND o.order_id =:order_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("order_id", order);
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ItemDTO> results = new ArrayList<>();
            while(rs.next()) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setId(rs.getInt("item_id"));
                itemDTO.setName(rs.getString("name"));
                itemDTO.setDescription(rs.getString("description"));
                itemDTO.setCategory(rs.getString("category"));
                itemDTO.setPrice(rs.getString("price"));
                itemDTO.setShop(rs.getString("shop"));
                itemDTO.setPhoto(rs.getString("photo"));
                itemDTO.setQuantity(rs.getInt("c.quantity"));
                itemDTO.setCartId(rs.getInt("c.cart_id"));

                results.add(itemDTO);
            }
            return results;

        });
    }

    @Override
    public void deleteFromCart(int cart_id){
        String sqlDelete = "" +
                "Delete " +
                "FROM cart " +
                "WHERE cart_id = :cart_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cart_id", cart_id);

        jdbcTemplate.update(sqlDelete, namedParameters);
    }

    @Override
    public void updateCartQuantity(CartDTO cartDTO){
        String sqlUpdate = "" +
                "UPDATE cart " +
                "SET quantity =:quantity " +
                "WHERE cart_id = :cart_id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cart_id", cartDTO.getCartId());
        namedParameters.addValue("quantity", cartDTO.getQuantity());

        jdbcTemplate.update(sqlUpdate, namedParameters);
    }

    @Override
    public float getTotalPriceByUserId(int userId){
        String sqlSelect = "" +
                "SELECT " +
                "    SUM(price) AS suma  " +
                "FROM orders " +
                "WHERE user_id =:user_id " +
                "AND state = 'complete' ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", userId);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            float results = 0;
            while(rs.next()) {
                results = rs.getFloat("suma");
            }
            return results;

        });
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "    *  " +
                "FROM orders " +
                "WHERE user_id =:user_id " +
                "AND state = 'complete' ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(rs.getInt("order_id"));
                orderDTO.setUserId(rs.getInt("user_id"));
                orderDTO.setPrice(rs.getFloat("price"));
                orderDTO.setState(rs.getString("state"));
                orderDTO.setDeliveryPerson(rs.getString("delivery_person"));
                orderDTO.setDeliveryAddress(rs.getString("delivery_address"));
                orderDTO.setBillingPerson(rs.getString("billing_person"));
                orderDTO.setBillingAddress(rs.getString("billing_address"));
                orderDTO.setCashPay(rs.getString("cash_pay"));
                orderDTO.setData(rs.getDate("data"));

                results.add(orderDTO);
            }
            return results;

        });
    }

    @Override
    public List<ItemDTO> getOrderItemsByOrderId(int order_id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM items i, orders o, cart c " +
                "WHERE o.order_id = c.order_id " +
                "AND c.item_id = i.item_id " +
                "AND o.order_id =:order_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("order_id", order_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ItemDTO> results = new ArrayList<>();
            while(rs.next()) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setId(rs.getInt("item_id"));
                itemDTO.setName(rs.getString("name"));
                itemDTO.setDescription(rs.getString("description"));
                itemDTO.setCategory(rs.getString("category"));
                itemDTO.setPrice(rs.getString("price"));
                itemDTO.setShop(rs.getString("shop"));
                itemDTO.setPhoto(rs.getString("photo"));
                itemDTO.setQuantity(rs.getInt("c.quantity"));
                itemDTO.setCartId(rs.getInt("c.cart_id"));

                results.add(itemDTO);
            }
            return results;

        });

    }

    @Override
    public List<OrderDTO> getAllOrders(){
        String sqlSelect = "" +
                "SELECT " +
                "    *  " +
                "FROM orders " +
                "WHERE state = 'complete' ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(rs.getInt("order_id"));
                orderDTO.setUserId(rs.getInt("user_id"));
                orderDTO.setPrice(rs.getFloat("price"));
                orderDTO.setState(rs.getString("state"));
                orderDTO.setDeliveryPerson(rs.getString("delivery_person"));
                orderDTO.setDeliveryAddress(rs.getString("delivery_address"));
                orderDTO.setBillingPerson(rs.getString("billing_person"));
                orderDTO.setBillingAddress(rs.getString("billing_address"));
                orderDTO.setCashPay(rs.getString("cash_pay"));
                orderDTO.setData(rs.getDate("data"));
                orderDTO.setItems(getOrderItemsByOrderId(orderDTO.getId()));

                results.add(orderDTO);
            }
            return results;

        });
    }

    @Override
    public List<ItemDTO> getOrderItemsByOrderIdAndShop(int order_id, String shop){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM items i, orders o, cart c " +
                "WHERE o.order_id = c.order_id " +
                "AND c.item_id = i.item_id " +
                "AND o.order_id =:order_id " +
                "AND i.shop =:shop";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("order_id", order_id);
        namedParameters.addValue("shop", shop);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ItemDTO> results = new ArrayList<>();
            while(rs.next()) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setId(rs.getInt("item_id"));
                itemDTO.setName(rs.getString("name"));
                itemDTO.setDescription(rs.getString("description"));
                itemDTO.setCategory(rs.getString("category"));
                itemDTO.setPrice(rs.getString("price"));
                itemDTO.setShop(rs.getString("shop"));
                itemDTO.setPhoto(rs.getString("photo"));
                itemDTO.setQuantity(rs.getInt("c.quantity"));
                itemDTO.setCartId(rs.getInt("c.cart_id"));

                results.add(itemDTO);
            }
            return results;

        });
    }

    @Override
    public List<OrderDTO> getOrdersFromShop(String shop){
        String sqlSelect = "" +
                "SELECT " +
                "    *  " +
                "FROM orders " +
                "WHERE state = 'complete' ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(rs.getInt("order_id"));
                orderDTO.setUserId(rs.getInt("user_id"));
                orderDTO.setPrice(rs.getFloat("price"));
                orderDTO.setState(rs.getString("state"));
                orderDTO.setDeliveryPerson(rs.getString("delivery_person"));
                orderDTO.setDeliveryAddress(rs.getString("delivery_address"));
                orderDTO.setBillingPerson(rs.getString("billing_person"));
                orderDTO.setBillingAddress(rs.getString("billing_address"));
                orderDTO.setCashPay(rs.getString("cash_pay"));
                orderDTO.setData(rs.getDate("data"));
                orderDTO.setItems(getOrderItemsByOrderIdAndShop(orderDTO.getId(), shop));

                if(orderDTO.getItems().size() != 0){
                    results.add(orderDTO);
                }

            }
            return results;

        });
    }

    @Override       
    public List<OrderDTO> getLastMonthOrdersFromShop(String shop){
        String sqlSelect = "" +
                "SELECT " +
                "    *  " +
                " FROM orders " +
                " WHERE state = 'complete' " +
                " AND data BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(rs.getInt("order_id"));
                orderDTO.setUserId(rs.getInt("user_id"));
                orderDTO.setPrice(rs.getFloat("price"));
                orderDTO.setState(rs.getString("state"));
                orderDTO.setDeliveryPerson(rs.getString("delivery_person"));
                orderDTO.setDeliveryAddress(rs.getString("delivery_address"));
                orderDTO.setBillingPerson(rs.getString("billing_person"));
                orderDTO.setBillingAddress(rs.getString("billing_address"));
                orderDTO.setCashPay(rs.getString("cash_pay"));
                orderDTO.setData(rs.getDate("data"));
                orderDTO.setItems(getOrderItemsByOrderIdAndShop(orderDTO.getId(), shop));

                if(orderDTO.getItems().size() != 0){
                    results.add(orderDTO);
                }

            }
            return results;

        });
    }
}
