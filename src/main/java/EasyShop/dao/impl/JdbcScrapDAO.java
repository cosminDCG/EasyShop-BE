package EasyShop.dao.impl;

import EasyShop.dao.ScrapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcScrapDAO implements ScrapDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void saveLink(String page){
        String sqlInsert = "" +
                "INSERT INTO scrap(page) VALUES( " +
                "    :page " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("page", page);

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public List<String> getAllLinks(){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM scrap ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<String> results = new ArrayList<>();
            while(rs.next()) {
                String link = rs.getString("page");
                results.add(link);
            }
            return results;

        });
    }
}
