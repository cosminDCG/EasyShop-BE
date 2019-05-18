package EasyShop.dao.impl;

import EasyShop.dao.UserDAO;
import EasyShop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDAO implements UserDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void registerUser(UserDTO user){
        String sqlInsert = "" +
                "INSERT INTO user(first_name, last_name,email, address, city, phone_number, password, photo, role, join_date) VALUES( " +
                "    :firstName, " +
                "    :lastName, " +
                "    :email, " +
                "    :address, " +
                "    :city, " +
                "    :phoneNumber, " +
                "    :password, " +
                "    'avatar.png', " +
                "    'user', " +
                "    SYSDATE() " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("firstName", user.getFirstName());
        namedParameters.addValue("lastName", user.getLastName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("address", user.getAddress());
        namedParameters.addValue("city", user.getCity());
        namedParameters.addValue("phoneNumber", user.getPhoneNumber());
        namedParameters.addValue("password", user.getPassword());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM user " +
                "WHERE user.email = :email ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", email);

        UserDTO userDTO = null;
        try {
            userDTO = jdbcTemplate.queryForObject(sqlSelect, namedParameters, new UserDTOMapper());

        } catch (EmptyResultDataAccessException ignored) {

        }

        return userDTO;

    }

    @Override
    public UserDTO getUserById(int id) {
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM user " +
                "WHERE user_id = :id ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        UserDTO userDTO = null;
        try {
            userDTO = jdbcTemplate.queryForObject(sqlSelect, namedParameters, new UserDTOMapper());
        } catch (EmptyResultDataAccessException ignored) {

        }

        return userDTO;
    }

    @Override
    public void updateUser(UserDTO userDTO){

        String sqlUpdate = "" +
                "UPDATE user " +
                "SET first_name = :firstName, " +
                " last_name = :lastName, " +
                " email = :email, " +
                " address = :address, " +
                " city = :city, " +
                " phone_number = :phoneNumber " +
                "WHERE user_id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("firstName", userDTO.getFirstName());
        namedParameters.addValue("lastName", userDTO.getLastName());
        namedParameters.addValue("email", userDTO.getEmail());
        namedParameters.addValue("address", userDTO.getAddress());
        namedParameters.addValue("city", userDTO.getCity());
        namedParameters.addValue("phoneNumber", userDTO.getPhoneNumber());
        namedParameters.addValue("id", userDTO.getId());

        jdbcTemplate.update(sqlUpdate,namedParameters);
    }

    @Override
    public void changePassword(int user_id, String password){
        String sqlUpdate = "" +
                "UPDATE user " +
                "SET password = :password "+
                "WHERE user_id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("password", password);
        namedParameters.addValue("id", user_id);

        jdbcTemplate.update(sqlUpdate,namedParameters);
    }

    @Override
    public void insertProfilePicture(String path, int id) {

        String sqlUpdate = "" +
                "UPDATE user " +
                "SET photo = :photo "+
                "WHERE user_id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("photo", path);
        namedParameters.addValue("id", id);

        jdbcTemplate.update(sqlUpdate,namedParameters);
    }

    @Override
    public void deleteUserById(int user_id){
        String sqlDelete = "" +
                "Delete " +
                "FROM user " +
                "WHERE user_id = :user_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user_id);

        jdbcTemplate.update(sqlDelete, namedParameters);
    }

    @Override
    public List<UserDTO> getAllUsers(){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM user ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<UserDTO> results = new ArrayList<>();
            while(rs.next()) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(rs.getInt("user_id"));
                userDTO.setEmail(rs.getString("email"));
                userDTO.setFirstName(rs.getString("first_name"));
                userDTO.setLastName(rs.getString("last_name"));
                userDTO.setPassword(rs.getString("password"));
                userDTO.setAddress(rs.getString("address"));
                userDTO.setCity(rs.getString("city"));
                userDTO.setPhoneNumber(rs.getString("phone_number"));
                userDTO.setPhoto(rs.getString("photo"));
                userDTO.setRole(rs.getString("role"));
                userDTO.setJoinDate(rs.getDate("join_date"));
                results.add(userDTO);
            }
            return results;

        });

    }

    class UserDTOMapper implements RowMapper<UserDTO> {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDTO user = new UserDTO();
            user.setId(rs.getInt("user_id"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setCity(rs.getString("city"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setPhoto(rs.getString("photo"));
            user.setRole(rs.getString("role"));
            user.setJoinDate(rs.getDate("join_date"));
            return user;
        }
    }
}
