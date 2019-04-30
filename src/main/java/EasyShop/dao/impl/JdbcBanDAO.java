package EasyShop.dao.impl;

import EasyShop.dao.BanDAO;
import EasyShop.dto.BanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
