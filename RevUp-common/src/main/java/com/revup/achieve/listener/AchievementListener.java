package com.revup.achieve.listener;

import com.revup.achieve.dto.AchievementEventInfo;

public interface AchievementListener {

    void execute(AchievementEventInfo info);
}
