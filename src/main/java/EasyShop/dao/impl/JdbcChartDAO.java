package EasyShop.dao.impl;

import EasyShop.dao.ChartDAO;
import EasyShop.dto.chart.OverallDateOrderDTO;
import EasyShop.dto.chart.ShopDateOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcChartDAO implements ChartDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<ShopDateOrderDTO> getShopOrdersPerWeek(String shop){
        String sqlSelect = "" +
                "SELECT COUNT(DISTINCT(o.order_id)) as order_no, SUM(c.quantity) as qty, o.data, o.order_id, i.item_id, c.item_id, c.order_id, i.shop " +
                "FROM orders o, cart c, items i " +
                "WHERE o.order_id = c.order_id " +
                "AND i.item_id = c.item_id " +
                "AND i.shop =:shop " +
                "GROUP BY o.data " +
                "HAVING o.data BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() ";


        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ShopDateOrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                ShopDateOrderDTO shopDateOrderDTO = new ShopDateOrderDTO();
                shopDateOrderDTO.setDate(rs.getDate("data"));
                shopDateOrderDTO.setStats(rs.getFloat("order_no"));
                shopDateOrderDTO.setQuantity(rs.getInt("qty"));

                results.add(shopDateOrderDTO);
            }
            return results;

        });
    }

    @Override
    public List<ShopDateOrderDTO> getShopOrdersPerMonth(String shop){
        String sqlSelect = "" +
                "SELECT COUNT(DISTINCT(o.order_id)) as order_no, SUM(c.quantity) as qty, o.data, o.order_id, i.item_id, c.item_id, c.order_id, i.shop " +
                "FROM orders o, cart c, items i " +
                "WHERE o.order_id = c.order_id " +
                "AND i.item_id = c.item_id " +
                "AND i.shop =:shop " +
                "GROUP BY o.data " +
                "HAVING o.data BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() ";


        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ShopDateOrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                ShopDateOrderDTO shopDateOrderDTO = new ShopDateOrderDTO();
                shopDateOrderDTO.setDate(rs.getDate("data"));
                shopDateOrderDTO.setStats(rs.getFloat("order_no"));
                shopDateOrderDTO.setQuantity(rs.getInt("qty"));

                results.add(shopDateOrderDTO);
            }
            return results;

        });
    }

    @Override
    public List<ShopDateOrderDTO> getShopOrdersPerYear(String shop){
        String sqlSelect = "" +
                "SELECT COUNT(DISTINCT(o.order_id)) as order_no, SUM(c.quantity) as qty, o.data, o.order_id, i.item_id, c.item_id, c.order_id, i.shop " +
                "FROM orders o, cart c, items i " +
                "WHERE o.order_id = c.order_id " +
                "AND i.item_id = c.item_id " +
                "AND i.shop =:shop " +
                "GROUP BY MONTH(o.data) " +
                "HAVING o.data BETWEEN DATE_SUB(NOW(), INTERVAL 365 DAY) AND NOW() ";


        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ShopDateOrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                ShopDateOrderDTO shopDateOrderDTO = new ShopDateOrderDTO();
                shopDateOrderDTO.setDate(rs.getDate("data"));
                shopDateOrderDTO.setStats(rs.getFloat("order_no"));
                shopDateOrderDTO.setQuantity(rs.getInt("qty"));

                results.add(shopDateOrderDTO);
            }
            return results;

        });
    }

    @Override
    public List<OverallDateOrderDTO> getEasyOrdersPerWeek(){
        String sqlSelect = "" +
                "SELECT COUNT(DISTINCT(o.order_id)) as order_no, SUM(c.quantity) as qty, o.data, o.order_id, i.item_id, c.item_id, c.order_id, i.shop " +
                "FROM orders o, cart c, items i " +
                "WHERE o.order_id = c.order_id " +
                "AND i.item_id = c.item_id " +
                "GROUP BY i.shop, o.data " +
                "HAVING o.data BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() ";


        MapSqlParameterSource namedParameters = new MapSqlParameterSource();


        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OverallDateOrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OverallDateOrderDTO overallDateOrderDTO = new OverallDateOrderDTO();
                overallDateOrderDTO.setDate(rs.getDate("data"));
                overallDateOrderDTO.setStats(rs.getFloat("order_no"));
                overallDateOrderDTO.setQuantity(rs.getInt("qty"));
                overallDateOrderDTO.setShop(rs.getString("shop"));

                results.add(overallDateOrderDTO);
            }
            return results;

        });
    }

    @Override
    public List<OverallDateOrderDTO> getEasyOrdersPerMonth(){
        String sqlSelect = "" +
                "SELECT COUNT(DISTINCT(o.order_id)) as order_no, SUM(c.quantity) as qty, o.data, o.order_id, i.item_id, c.item_id, c.order_id, i.shop " +
                "FROM orders o, cart c, items i " +
                "WHERE o.order_id = c.order_id " +
                "AND i.item_id = c.item_id " +
                "GROUP BY i.shop, o.data " +
                "HAVING o.data BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() ";


        MapSqlParameterSource namedParameters = new MapSqlParameterSource();


        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OverallDateOrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OverallDateOrderDTO overallDateOrderDTO = new OverallDateOrderDTO();
                overallDateOrderDTO.setDate(rs.getDate("data"));
                overallDateOrderDTO.setStats(rs.getFloat("order_no"));
                overallDateOrderDTO.setQuantity(rs.getInt("qty"));
                overallDateOrderDTO.setShop(rs.getString("shop"));

                results.add(overallDateOrderDTO);
            }
            return results;

        });
    }

    @Override
    public List<OverallDateOrderDTO> getEasyOrdersPerYear(){
        String sqlSelect = "" +
                "SELECT COUNT(DISTINCT(o.order_id)) as order_no, SUM(c.quantity) as qty, o.data, o.order_id, i.item_id, c.item_id, c.order_id, i.shop " +
                "FROM orders o, cart c, items i " +
                "WHERE o.order_id = c.order_id " +
                "AND i.item_id = c.item_id " +
                "GROUP BY i.shop, MONTH(o.data) " +
                "HAVING o.data BETWEEN DATE_SUB(NOW(), INTERVAL 365 DAY) AND NOW() ";


        MapSqlParameterSource namedParameters = new MapSqlParameterSource();


        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<OverallDateOrderDTO> results = new ArrayList<>();
            while(rs.next()) {
                OverallDateOrderDTO overallDateOrderDTO = new OverallDateOrderDTO();
                overallDateOrderDTO.setDate(rs.getDate("data"));
                overallDateOrderDTO.setStats(rs.getFloat("order_no"));
                overallDateOrderDTO.setQuantity(rs.getInt("qty"));
                overallDateOrderDTO.setShop(rs.getString("shop"));

                results.add(overallDateOrderDTO);
            }
            return results;

        });
    }
}
