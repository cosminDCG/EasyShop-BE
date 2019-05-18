package EasyShop.dao.impl;

import EasyShop.dao.ItemDAO;
import EasyShop.dto.ItemDTO;
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
public class JdbcItemDAO implements ItemDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertItem(ItemDTO itemDTO) {

        String sqlInsert = "" +
                "INSERT INTO items(name, description, category, price, shop, photo) VALUES( " +
                "    :name, " +
                "    :description, " +
                "    :category, " +
                "    :price, " +
                "    :shop, " +
                "    :photo " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", itemDTO.getName());
        namedParameters.addValue("description", itemDTO.getDescription());
        namedParameters.addValue("category", itemDTO.getCategory());
        namedParameters.addValue("price", itemDTO.getPrice());
        namedParameters.addValue("shop", itemDTO.getShop());
        namedParameters.addValue("photo", itemDTO.getPhoto());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public int getItemCount() {
        int count = 0;

        String sqlSelect = "" +
                "SELECT " +
                "    ifnull(max(item_id), 0 ) " +
                "FROM items ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        count = jdbcTemplate.queryForObject(sqlSelect, namedParameters, Integer.class);
        return count;
    }

    @Override
    public int getReviewSum(int item_id){
        String sqlSelect = "" +
                "SELECT " +
                "    SUM(review) as sum_rev " +
                "FROM review " +
                "WHERE review <> 0 " +
                "AND reply_to = 0 " +
                "AND item_id = :item_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", item_id);
        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int result = 0;
            while(rs.next()) {
                result = rs.getInt("sum_rev");
            }
            return result;

        });

    }

    @Override
    public int getReviewCount(int item_id){
        String sqlSelect = "" +
                "SELECT " +
                "    COUNT(review_id) as reviewers " +
                "FROM review " +
                "WHERE review <> 0 " +
                "AND reply_to = 0 " +
                "AND item_id = :item_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", item_id);
        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int result = 0;
            while(rs.next()) {
                result = rs.getInt("reviewers");
            }
            return result;

        });
    }

    @Override
    public List<ItemDTO> getAllItems(){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM items " +
                "ORDER BY name";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

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
                if(getReviewCount(itemDTO.getId()) != 0) {
                    itemDTO.setReview((float)getReviewSum(itemDTO.getId()) / getReviewCount(itemDTO.getId()));
                }
                    itemDTO.setReviewers(getReviewCount(itemDTO.getId()));

                results.add(itemDTO);
            }
            return results;

        });
    }

    @Override
    public List<String> getAllCategories(){
        String sqlSelect = "" +
                "SELECT " +
                "    distinct(category) " +
                "FROM items " +
                "ORDER BY category";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<String> results = new ArrayList<>();
            while(rs.next()) {
                String category = rs.getString("category");
                results.add(category);
            }
            return results;

        });
    }

    @Override
    public ItemDTO getCheapestChoice(String criteria){
        String sqlSelect = "" +
                "SELECT *, match(name) against(:criteria) as relevance, " +
                " CAST(REPLACE(REPLACE(SUBSTRING_INDEX(price, 'Lei', 1),'.',''),',','.') AS DECIMAL(10,2)) AS order_price " +
                "FROM items " +
                "WHERE match(name) against(:criteria) " +
                "ORDER BY relevance desc, order_price asc " +
                "LIMIT 1";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("criteria", criteria);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            ItemDTO results = new ItemDTO();
            while(rs.next()) {
                results.setId(rs.getInt("item_id"));
                results.setName(rs.getString("name"));
                results.setDescription(rs.getString("description"));
                results.setCategory(rs.getString("category"));
                results.setPrice(rs.getString("price"));
                results.setShop(rs.getString("shop"));
                results.setPhoto(rs.getString("photo"));
            }
            return results;

        });
    }

    @Override
    public List<String> getAllShops(){
        String sqlSelect = "" +
                "SELECT " +
                "    distinct(shop) " +
                "FROM items ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<String> results = new ArrayList<>();
            while(rs.next()) {
                String shop = rs.getString("shop");
                results.add(shop);
            }
            return results;

        });
    }

    @Override
    public ItemDTO getCheapestSinglePlace(String criteria, String place){
        String sqlSelect = "" +
                "SELECT *, match(name) against(:criteria) as relevance, " +
                " CAST(REPLACE(REPLACE(SUBSTRING_INDEX(price, 'Lei', 1),'.',''),',','.')AS DECIMAL(10,2)) AS order_price " +
                "FROM items " +
                "WHERE match(name) against(:criteria) " +
                "AND shop = :place " +
                "ORDER BY relevance desc, order_price asc " +
                "LIMIT 1";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("criteria", criteria);
        namedParameters.addValue("place", place);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            ItemDTO results = new ItemDTO();
            while(rs.next()) {
                results.setId(rs.getInt("item_id"));
                results.setName(rs.getString("name"));
                results.setDescription(rs.getString("description"));
                results.setCategory(rs.getString("category"));
                results.setPrice(rs.getString("price"));
                results.setShop(rs.getString("shop"));
                results.setPhoto(rs.getString("photo"));
            }
            return results;

        });
    }

    @Override
    public void truncateItems(){
        String sqlTruncate = "" +
                "TRUNCATE TABLE items ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        jdbcTemplate.update(sqlTruncate, namedParameters);
    }

    class ItemDTOMapper implements RowMapper<ItemDTO> {
        @Override
        public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(rs.getInt("item_id"));
            itemDTO.setName(rs.getString("name"));
            itemDTO.setDescription(rs.getString("description"));
            itemDTO.setCategory(rs.getString("category"));
            itemDTO.setPrice(rs.getString("price"));
            itemDTO.setShop(rs.getString("shop"));
            itemDTO.setPhoto(rs.getString("photo"));
            return itemDTO;
        }
    }

}
