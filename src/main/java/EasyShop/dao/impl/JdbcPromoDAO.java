package EasyShop.dao.impl;

import EasyShop.dao.PromoDAO;
import EasyShop.dto.PromoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcPromoDAO implements PromoDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertPromo(PromoDTO promoDTO){
        String sqlInsert = "" +
                "INSERT INTO promo(user_id,promo_code, promo_percent, state) VALUES( " +
                "    :user_id, " +
                "    :promo_code, " +
                "    :promo_percent, " +
                "    'unused' " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", promoDTO.getUserId());
        namedParameters.addValue("promo_code", promoDTO.getPromoCode());
        namedParameters.addValue("promo_percent", promoDTO.getPromoPercent());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public PromoDTO verifyPromoAndGetPercent(int userId, String promoCode){
        String sqlSelect = "" +
                "SELECT " +
                "   promo_id, promo_percent " +
                "FROM promo " +
                "WHERE user_id =:user_id " +
                "AND promo_code =:promo_code " +
                "AND state = 'unused' ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", userId);
        namedParameters.addValue("promo_code", promoCode);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            PromoDTO results = new PromoDTO();
            while(rs.next()) {
                 results.setPromoPercent(rs.getInt("promo_percent"));
                 results.setId(rs.getInt("promo_id"));
            }
            return results;

        });
    }

    @Override
    public void useCode(PromoDTO promoDTO){
        String sqlUpdate = "" +
                "UPDATE promo " +
                "SET state ='used'" +
                "WHERE promo_id = :promo_id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("promo_id", promoDTO.getId());

        jdbcTemplate.update(sqlUpdate, namedParameters);
    }

    @Override
    public List<PromoDTO> getPromosByUserId(int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM promo " +
                "WHERE user_id =:user_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<PromoDTO> results = new ArrayList<>();
            while(rs.next()) {
                PromoDTO promoDTO = new PromoDTO();
                promoDTO.setId(rs.getInt("promo_id"));
                promoDTO.setUserId(rs.getInt("user_id"));
                promoDTO.setPromoCode(rs.getString("promo_code"));
                promoDTO.setPromoPercent(rs.getInt("promo_percent"));
                promoDTO.setState(rs.getString("state"));

                results.add(promoDTO);
            }
            return results;

        });
    }
}
