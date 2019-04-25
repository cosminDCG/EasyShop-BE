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
    public void insertCart(int user_id, int item_id, int quantity){

        int order = 0;
        if(getInProgressOrderByUserId(user_id) == 0){
            insertOrder(user_id);
            order = getInProgressOrderByUserId(user_id);
        } else order = getInProgressOrderByUserId(user_id);

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
}
