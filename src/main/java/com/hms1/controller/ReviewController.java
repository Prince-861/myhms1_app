package com.hms1.controller;

import com.hms1.entity.AppUser;
import com.hms1.entity.Property;
import com.hms1.entity.Review;
import com.hms1.repository.PropertyRepository;
import com.hms1.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
//    In order to get the Property id details we will have to use the Property Repository.
    private PropertyRepository propertyRepository;
    private ReviewRepository reviewRepository;

    public ReviewController(PropertyRepository propertyRepository, ReviewRepository reviewRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

//    http://localhost:8080/api/v1/review?propertyId=1
    @PostMapping
    public ResponseEntity<Review> write(
            @RequestBody Review review, //this will come as a JSON
            @RequestParam long propertyId, //this we will have to supply in the url
            @AuthenticationPrincipal AppUser user //using this arguments, based on the JWT token, the app details will automatically come here but we will have to supply the propertyId in the url and the review will come as the JSON.
    ){
//        The next thing is we will have to take the review and we will have to save it to the database.
//        but we are not only saving the reviews in the database, there are foreign keys as well, so just saving the reviews is not enough. We will have to save the foreign keys  details as well.
//        The user-details has automatically generated with the help of @AuthenticationPrincipal, but we will have to get the property details.

        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
        review.setProperty(property);//we have review object with the foreign key property object.
        review.setAppUser(user);//now we have review object with the foreign key user object.

//      now finally save the review
        Review savedReview = reviewRepository.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.OK);
    }
}
