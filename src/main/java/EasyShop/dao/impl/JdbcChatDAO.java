package EasyShop.dao.impl;

import EasyShop.dao.ChatDAO;
import EasyShop.dto.ChatDTO;
import EasyShop.dto.ChatListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcChatDAO implements ChatDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertMessage(ChatDTO chatDTO){

        String sqlInsert = "" +
                "INSERT INTO message(from_user, to_user, text, send_date) VALUES( " +
                "    :from_user, " +
                "    :to_user, " +
                "    :text, " +
                "    SYSDATE() " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("from_user", chatDTO.getFromUser());
        namedParameters.addValue("to_user", chatDTO.getToUser());
        namedParameters.addValue("text", chatDTO.getText());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public List<ChatDTO> getConversation(int from_user, int to_user){

        String sqlSelect = "" +
                "SELECT " +
                "  * " +
                "FROM message " +
                "WHERE (from_user=:from_user or from_user=:to_user) " +
                "AND (to_user=:to_user or to_user=:from_user) ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("from_user", from_user);
        namedParameters.addValue("to_user", to_user);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ChatDTO> results = new ArrayList<>();
            while(rs.next()) {
                ChatDTO chatDTO = new ChatDTO();
                chatDTO.setId(rs.getInt("message_id"));
                chatDTO.setFromUser(rs.getInt("from_user"));
                chatDTO.setToUser(rs.getInt("to_user"));
                chatDTO.setText(rs.getString("text"));
                chatDTO.setSendDate(rs.getDate("send_date"));

                results.add(chatDTO);
            }
            return results;

        });
    }
    
}
