package com.evdealer.ev_dealer_management.rating.controller;

import com.evdealer.ev_dealer_management.rating.model.dto.RatingDetailsGetDto;
import com.evdealer.ev_dealer_management.rating.model.dto.RatingListDto;
import com.evdealer.ev_dealer_management.rating.model.dto.RatingPostDto;
import com.evdealer.ev_dealer_management.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/all")
    public ResponseEntity<RatingListDto> getAllRatings(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        RatingListDto ratingList = ratingService.getAllRating(pageNo, pageSize);
        return ResponseEntity.ok(ratingList); // 200 OK
    }

    @PostMapping("/create-rating")
    public ResponseEntity<RatingDetailsGetDto> createRating(@RequestBody RatingPostDto ratingPostDto) {
        RatingDetailsGetDto createdRating = ratingService.createRating(ratingPostDto);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED); // 201 Created
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable("id") Long ratingId) {
        ratingService.deleteRatingById(ratingId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
