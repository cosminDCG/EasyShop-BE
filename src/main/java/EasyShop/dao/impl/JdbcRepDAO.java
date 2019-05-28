package EasyShop.dao.impl;

import EasyShop.dao.RepDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcRepDAO implements RepDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertRep(int user_id, String shop){
        String sqlInsert = "" +
                "INSERT INTO rep(user_id, shop) VALUES( " +
                "    :user_id, " +
                "    :shop " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);
        namedParameters.addValue("shop", shop);

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public int getShopRep(String shop){
        String sqlSelect = "" +
                "SELECT " +
                "    rep_id " +
                "FROM rep " +
                "WHERE shop = :shop ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            int results = 0;
            while(rs.next()) {
                results = rs.getInt("rep_id");
            }
            return results;

        });
    }

    @Override
    public String getShopByRepId(int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "    shop " +
                "FROM rep " +
                "WHERE user_id = :user_id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            String results = "";
            while(rs.next()) {
                results = rs.getString("shop");
            }
            return results;

        });
    }

    @Override
    public void deleteRep(String shop){
        String sqlDelete = "" +
                "Delete " +
                "FROM rep " +
                "WHERE shop = :shop";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        jdbcTemplate.update(sqlDelete, namedParameters);
    }
}
