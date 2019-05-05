package EasyShop.dao.impl;

import EasyShop.dao.ItemPropertiesDAO;
import EasyShop.dto.ItemPropertiesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcItemPropertiesDAO implements ItemPropertiesDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertProperties(List<ItemPropertiesDTO> itemPropertiesDTOS){

        for (ItemPropertiesDTO itemPropertiesDTO : itemPropertiesDTOS){

            String sqlInsert = "" +
                    "INSERT INTO item_properties(item_id, name, description) VALUES( " +
                    "    :item_id, " +
                    "    :name, " +
                    "    :description " +
                    ")";

            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("item_id", itemPropertiesDTO.getItemId());
            namedParameters.addValue("name", itemPropertiesDTO.getName());
            namedParameters.addValue("description", itemPropertiesDTO.getDescription());

            jdbcTemplate.update(sqlInsert, namedParameters);

        }
    }

    @Override
    public List<ItemPropertiesDTO> getPropertiesByProductId(int id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM item_properties " +
                "WHERE item_id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ItemPropertiesDTO> results = new ArrayList<>();
            while(rs.next()) {
                ItemPropertiesDTO itemDTO = new ItemPropertiesDTO();
                itemDTO.setId(rs.getInt("property_id"));
                itemDTO.setItemId(rs.getInt("item_id"));
                itemDTO.setName(rs.getString("name"));
                itemDTO.setDescription(rs.getString("description"));

                results.add(itemDTO);
            }
            return results;

        });
    }

    @Override
    public void truncateProperties(){
        String sqlTruncate = "" +
                "TRUNCATE TABLE item_properties ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        jdbcTemplate.update(sqlTruncate, namedParameters);
    }
}
