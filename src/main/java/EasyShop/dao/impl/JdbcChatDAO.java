package EasyShop.dao.impl;

import EasyShop.dao.ChatDAO;
import EasyShop.dao.UserDAO;
import EasyShop.dto.ChatDTO;
import EasyShop.dto.ChatListDTO;
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
public class JdbcChatDAO implements ChatDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private UserDAO userDAO;

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

    public List<Integer> getChatPartners(int user_id){

        String sqlSelect = "" +
                "SELECT " +
                "  DISTINCT from_user, to_user " +
                "FROM message " +
                "WHERE from_user=:id or from_user=:id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<Integer> results = new ArrayList<>();
            while(rs.next()) {
                int toAdd = 0;
                ChatDTO chatDTO = new ChatDTO();
                chatDTO.setFromUser(rs.getInt("from_user"));
                chatDTO.setToUser(rs.getInt("to_user"));
                if(chatDTO.getFromUser() != user_id){
                    results.add(chatDTO.getFromUser());
                }

                if(chatDTO.getToUser() != user_id){
                    results.add(chatDTO.getToUser());
                }

            }
            return results;

        });
    }

    public ChatListDTO getLastMessageById(int id, int user_id){
        String sqlSelect = "" +
                "SELECT " +
                "  * " +
                "FROM message " +
                "WHERE (from_user=:id or to_user=:id) and (from_user=:user_id or to_user=:user_id) " +
                "ORDER BY send_date desc " +
                "LIMIT 1 ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        namedParameters.addValue("user_id", user_id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            ChatListDTO results = new ChatListDTO();
            while(rs.next()) {
                results.setId(rs.getInt("message_id"));
                results.setLastMessage(rs.getString("text"));
                results.setUser(userDAO.getUserById(id));
                results.setConversation(getConversation(user_id, id));

            }
            return results;

        });
    }

    @Override
    public List<ChatListDTO> getChatHistory(int user_id){
        List<Integer> users = getChatPartners(user_id);
        List<ChatListDTO> chatHistory = new ArrayList<>();

        for(int id : users){
            chatHistory.add(getLastMessageById(id, user_id));
        }
        
        return chatHistory;
    }
    
    
}
