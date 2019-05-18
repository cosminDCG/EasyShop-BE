package EasyShop.service.impl;

import EasyShop.dao.ReviewDAO;
import EasyShop.dto.ReviewDTO;
import EasyShop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDAO reviewDAO;

    public Boolean insertReview(ReviewDTO reviewDTO){

        reviewDAO.insertReview(reviewDTO);
        return true;
    }

    public List<ReviewDTO> getReviewsByItemId(int id){
        return reviewDAO.getReviewsByItemId(id);
    }

    public Boolean deleteReviewById(int review_id){

        reviewDAO.deleteReviewById(review_id);
        return true;
    }

    public int getNoOfReviewsById(int user_id){
        return reviewDAO.getNoOfReviewsById(user_id);
    }

    public int getNoOfCommentsById(int user_id){
        return reviewDAO.getNoOfCommentsById(user_id);
    }
}
