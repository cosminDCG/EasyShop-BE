package EasyShop.dao.impl;

import EasyShop.dao.WishlistDAO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.WishItemDTO;
import EasyShop.dto.WishlistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcWishlistDAO implements WishlistDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertWishItem(WishlistDTO wishlistDTO){

        String sqlInsert = "" +
                "INSERT INTO wishlist(item_id, user_id, current_price, expected_price) VALUES( " +
                "    :item_id, " +
                "    :user_id, " +
                "    :current_price, " +
                "    :expected_price " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", wishlistDTO.getItemId());
        namedParameters.addValue("user_id", wishlistDTO.getUserId());
        namedParameters.addValue("current_price", wishlistDTO.getCurrentPrice());
        namedParameters.addValue("expected_price", wishlistDTO.getExpectedPrice());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public List<WishItemDTO> getWishItemsForUser(int user_id) {
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                " FROM items i, wishlist w " +
                " where i.item_id = w.item_id " +
                " AND w.user_id = :user_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<WishItemDTO> results = new ArrayList<>();
            while(rs.next()) {
                WishItemDTO itemDTO = new WishItemDTO();
                itemDTO.setId(rs.getInt("item_id"));
                itemDTO.setName(rs.getString("name"));
                itemDTO.setDescription(rs.getString("description"));
                itemDTO.setCategory(rs.getString("category"));
                itemDTO.setPrice(rs.getString("price"));
                itemDTO.setShop(rs.getString("shop"));
                itemDTO.setPhoto(rs.getString("photo"));
                itemDTO.setWishId(rs.getInt("wish_id"));
                itemDTO.setExpectedPrice(rs.getString("expected_price"));
                results.add(itemDTO);
            }
            return results;

        });
    }

    @Override
    public void deleteWishItem(int wish_id) {

        String sqlDelete = "" +
                "Delete " +
                "FROM wishlist " +
                "WHERE wish_id = :wish_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("wish_id", wish_id);

        jdbcTemplate.update(sqlDelete, namedParameters);
    }

    @Override
    public int getWishIdByItemId(int item_id){
        String sqlSelect = "" +
                "SELECT " +
                "    wish_id " +
                " FROM wishlist" +
                " where item_id = :item_id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", item_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int results = 0;
            while(rs.next()) {
                results = rs.getInt("wish_id");
            }
            return results;

        });
    }

    class WishlistDTOMapper implements RowMapper<WishlistDTO> {
        @Override
        public WishlistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            WishlistDTO wishlistDTO = new WishlistDTO();
            wishlistDTO.setWishId(rs.getInt("wish_id"));
            wishlistDTO.setItemId(rs.getInt("item_id"));
            wishlistDTO.setUserId(rs.getInt("user_id"));
            wishlistDTO.setCurrentPrice(rs.getString("current_price"));
            wishlistDTO.setExpectedPrice(rs.getString("expected_price"));
            return wishlistDTO;
        }
    }
}
