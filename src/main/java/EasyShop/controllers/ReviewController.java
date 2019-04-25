package EasyShop.controllers;

import EasyShop.dto.ReviewDTO;
import EasyShop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/review/insert", method = RequestMethod.POST)
    public ResponseEntity insertReview(@RequestBody ReviewDTO reviewDTO){
        Boolean ok = reviewService.insertReview(reviewDTO);
        if(ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/review/all", method = RequestMethod.GET)
    public ResponseEntity getReviewsByItemId(@RequestParam int id){
        List<ReviewDTO> reviewDTOS = reviewService.getReviewsByItemId(id);
        return new ResponseEntity(reviewDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/review/delete", method = RequestMethod.POST)
    public ResponseEntity deleteReviewById(@RequestParam int id){
        Boolean ok = reviewService.deleteReviewById(id);
        if(ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
