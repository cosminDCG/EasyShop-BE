package EasyShop.dao;

import EasyShop.dto.ReviewDTO;

import java.util.List;

public interface ReviewDAO {

    void insertReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getRepliesByReviewId(int id);

    List<ReviewDTO> getReviewsByItemId(int id);

    void deleteReviewById(int review_id);

    int getNoOfReviewsById(int user_id);

    int getNoOfCommentsById(int user_id);
}
