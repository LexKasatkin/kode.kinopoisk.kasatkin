package com.kodekinopoiskkasatkin;

/**
 * Created by root on 13.10.16.
 */
public class RatingData {
    String ratingGoodReview;
    String getRatingGoodReviewVoteCount;
    String rating;
    String ratingVoteCount;
    String ratingAwait;
    String ratingAwaitCount;
    String ratingFilmCritics;
    String getRatingFilmCriticsVoteCounts;

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setGetRatingFilmCriticsVoteCounts(String getRatingFilmCriticsVoteCounts) {
        this.getRatingFilmCriticsVoteCounts = getRatingFilmCriticsVoteCounts;
    }

    public void setGetRatingGoodReviewVoteCount(String getRatingGoodReviewVoteCount) {
        this.getRatingGoodReviewVoteCount = getRatingGoodReviewVoteCount;
    }

    public void setRatingAwait(String ratingAwait) {
        this.ratingAwait = ratingAwait;
    }

    public void setRatingAwaitCount(String ratingAwaitCount) {
        this.ratingAwaitCount = ratingAwaitCount;
    }

    public void setRatingFilmCritics(String ratingFilmCritics) {
        this.ratingFilmCritics = ratingFilmCritics;
    }

    public void setRatingGoodReview(String ratingGoodReview) {
        this.ratingGoodReview = ratingGoodReview;
    }

    public void setRatingVoteCount(String ratingVoteCount) {
        this.ratingVoteCount = ratingVoteCount;
    }
}
