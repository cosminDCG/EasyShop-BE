package EasyShop.service;

import EasyShop.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    Boolean insertReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewsByItemId(int id);

    Boolean deleteReviewById(int review_id);

    int getNoOfReviewsById(int user_id);

    int getNoOfCommentsById(int user_id);
}
