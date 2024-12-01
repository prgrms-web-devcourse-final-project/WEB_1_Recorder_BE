package com.revup.answer.enums;

import com.revup.answer.exception.InvalidAdoptedReviewException;

public enum AdoptedReview {
    // 이해하기 쉬워요
    EASY,
    // 유용해요
    USEFUL,
    //도움이 많이 됐어요
    HELPFUL,
    ;

    public static AdoptedReview from(String review) {
        try {
           return AdoptedReview.valueOf(review.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new InvalidAdoptedReviewException();
        }
    }
}
