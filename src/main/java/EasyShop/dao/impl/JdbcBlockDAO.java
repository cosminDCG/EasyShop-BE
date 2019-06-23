package EasyShop.dao.impl;

import EasyShop.dao.BlockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcBlockDAO implements BlockDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insertBlock(String shop){
        String sqlInsert = "" +
                "INSERT INTO block(shop) VALUES( " +
                "    :shop " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public void deleteBlock(String shop){
        String sqlDelete = "" +
                "Delete " +
                "FROM block " +
                "WHERE shop = :shop";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        jdbcTemplate.update(sqlDelete, namedParameters);
    }

    @Override
    public List<String> getBlockedShops(){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM block ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
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
    public int checkIfShopIsBlocked(String shop){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM block " +
                " WHERE shop=:shop ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        return jdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int results = 0;
            while(rs.next()) {
                results = 1;
            }
            return results;

        });
    }
}
