package com.revup.feedback.repository;

import java.util.List;

public interface CustomFeedBackRepository {

    List<Long> findRequestedFeedBackIdsByUserId(Long userId);
}
