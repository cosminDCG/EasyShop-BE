package EasyShop.dao.impl;

import EasyShop.dao.BanDAO;
import EasyShop.dto.BanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcBanDAO implements BanDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void addBan(BanDTO banDTO){
        String sqlInsert = "" +
                "INSERT INTO ban(banned_by, banned_user, end_date, reason) VALUES( " +
                "    :banned_by, " +
                "    :banned_user, " +
                "    SYSDATE() + INTERVAL :end_date DAY, " +
                "    :reason " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("banned_by", banDTO.getBannedBy());
        namedParameters.addValue("banned_user", banDTO.getBannedUser());
        namedParameters.addValue("end_date", banDTO.getBanDays());
        namedParameters.addValue("reason", banDTO.getReason());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public BanDTO getActiveBanByUserId(int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                " FROM ban" +
                " WHERE banned_user = :user_id " +
                " AND end_date > sysdate()";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            BanDTO results = new BanDTO();
            while(rs.next()) {
                results.setId(rs.getInt("ban_id"));
                results.setBannedBy(rs.getInt("banned_by"));
                results.setBannedUser(rs.getInt("banned_user"));
                results.setEndDate(rs.getDate("end_date"));
                results.setReason(rs.getString("reason"));
            }
            return results;

        });
    }

    @Override
    public void updateBanDate(int ban_id, int days, String reason){
        String sqlUpdate = "" +
                "UPDATE ban " +
                "SET end_date = end_date + INTERVAL :days DAY, " +
                " reason = :reason " +
                "WHERE ban_id = :ban_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("days", days);
        namedParameters.addValue("reason", reason);
        namedParameters.addValue("ban_id", ban_id);

        jdbcTemplate.update(sqlUpdate,namedParameters);
    }
}
