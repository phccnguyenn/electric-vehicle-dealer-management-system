package com.evdealer.ev_dealer_management.rating.service;

import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.rating.model.Rating;
import com.evdealer.ev_dealer_management.rating.model.dto.RatingDetailsGetDto;
import com.evdealer.ev_dealer_management.rating.model.dto.RatingListDto;
import com.evdealer.ev_dealer_management.rating.model.dto.RatingPostDto;
import com.evdealer.ev_dealer_management.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingListDto getAllRating(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Rating> ratingPage = ratingRepository.findAllByOrderByCreatedOnDesc(pageable);

        List<RatingDetailsGetDto> ratingInfoGetDtos = ratingPage.getContent()
                .stream()
                .map(RatingDetailsGetDto::fromModel)
                .toList();

        return new RatingListDto(
                ratingInfoGetDtos,
                ratingPage.getNumber(),
                ratingPage.getSize(),
                (int) ratingPage.getTotalElements(),
                ratingPage.getTotalPages(),
                ratingPage.isLast()
        );
    }

    public RatingDetailsGetDto createRating(RatingPostDto ratingPostDto) {
        Rating rating = new Rating();
        rating.setPhone(ratingPostDto.phone());
        rating.setContent(ratingPostDto.content());

        return RatingDetailsGetDto.fromModel(ratingRepository.save(rating));
    }

    public void deleteRatingById(Long ratingId) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.RATING_NOT_FOUND));
        ratingRepository.delete(existingRating);
    }

}
