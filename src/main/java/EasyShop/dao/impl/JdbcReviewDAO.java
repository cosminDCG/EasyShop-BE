package EasyShop.dao.impl;

import EasyShop.dao.ReviewDAO;
import EasyShop.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcReviewDAO implements ReviewDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void insertReview(ReviewDTO reviewDTO){

        String sqlInsert = "" +
                "INSERT INTO review(item_id, user_id, first_name, last_name, photo, comment, review, reply_to, data) VALUES( " +
                "    :item_id, " +
                "    :user_id, " +
                "    :first_name, " +
                "    :last_name, " +
                "    :photo, " +
                "    :comment, " +
                "    :review, " +
                "    :reply_to, " +
                "    :data " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", reviewDTO.getProductId());
        namedParameters.addValue("user_id", reviewDTO.getUserId());
        namedParameters.addValue("first_name", reviewDTO.getFirstName());
        namedParameters.addValue("last_name", reviewDTO.getLastName());
        namedParameters.addValue("photo", reviewDTO.getPhoto());
        namedParameters.addValue("comment", reviewDTO.getComment());
        namedParameters.addValue("review", reviewDTO.getReview());
        namedParameters.addValue("reply_to", reviewDTO.getReplyTo());
        namedParameters.addValue("data", reviewDTO.getData());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public List<ReviewDTO> getRepliesByReviewId(int id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM review " +
                "WHERE reply_to = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ReviewDTO> results = new ArrayList<>();
            while(rs.next()) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setId(rs.getInt("review_id"));
                reviewDTO.setProductId(rs.getInt("item_id"));
                reviewDTO.setUserId(rs.getInt("user_id"));
                reviewDTO.setFirstName(rs.getString("first_name"));
                reviewDTO.setLastName(rs.getString("last_name"));
                reviewDTO.setPhoto(rs.getString("photo"));
                reviewDTO.setComment(rs.getString("comment"));
                reviewDTO.setReview((rs.getInt("review")));
                reviewDTO.setReplyTo(rs.getInt("reply_to"));
                reviewDTO.setData(rs.getString("data"));
                results.add(reviewDTO);
            }
            return results;

        });
    }

    @Override
    public  List<ReviewDTO> getReviewsByItemId(int id){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM review " +
                "WHERE item_id = :id " +
                "AND reply_to = 0";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        return namedJdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<ReviewDTO> results = new ArrayList<>();
            while(rs.next()) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setId(rs.getInt("review_id"));
                reviewDTO.setProductId(rs.getInt("item_id"));
                reviewDTO.setUserId(rs.getInt("user_id"));
                reviewDTO.setFirstName(rs.getString("first_name"));
                reviewDTO.setLastName(rs.getString("last_name"));
                reviewDTO.setPhoto(rs.getString("photo"));
                reviewDTO.setComment(rs.getString("comment"));
                reviewDTO.setReview((rs.getInt("review")));
                reviewDTO.setReplyTo(rs.getInt("reply_to"));
                reviewDTO.setData(rs.getString("data"));
                List<ReviewDTO> replies = getRepliesByReviewId(reviewDTO.getId());
                reviewDTO.setReplies(replies);
                results.add(reviewDTO);
            }
            return results;

        });
    }

    @Override
    public void deleteReviewById(int review_id){
        String sqlDeleteReview = "" +
                "Delete " +
                "FROM review " +
                "WHERE review_id = :review_id";

        String sqlDeleteReplies = "" +
                "Delete " +
                "FROM review " +
                "WHERE reply_to = :review_id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("review_id", review_id);

        jdbcTemplate.update(sqlDeleteReview, namedParameters);
        jdbcTemplate.update(sqlDeleteReplies, namedParameters);
    }
}
