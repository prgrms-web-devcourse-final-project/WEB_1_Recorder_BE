package com.revup.feedback.usecase;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.request.FeedbackCodeUpdateRequest;
import com.revup.feedback.service.FeedbackCodeService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UpdateFeedbackCodeUseCase {

    private final FeedbackCodeService feedbackCodeService;

    private final RedissonClient redissonClient;
    private static final String LOCK_KEY = "feedbackCodeLock";

    // TODO: 락 거는거 나중에 AOP 할수도 있겠다
    public Long execute(Long feedbackCodeId, FeedbackCodeUpdateRequest feedbackCodeUpdateRequest) {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        boolean locked = false;

        try {
            locked = lock.tryLock(0, 2, TimeUnit.SECONDS);

            if (!locked) {
                System.out.println("다른 클라이언트가 수정 중입니다. 요청을 무시합니다.");
                return null;
            }

            return feedbackCodeService.feedbackCodeUpdate(feedbackCodeId, feedbackCodeUpdateRequest.getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.INTERRUPTED);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

}
