package com.revup.heart.batch;

import com.revup.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartBatchScheduler {
    private final HeartService heartService;

    @Scheduled(cron = "0 */1 * * * *")
    public void updateHeartsCount(){
        heartService.updateHeartsCount();
    }
}
