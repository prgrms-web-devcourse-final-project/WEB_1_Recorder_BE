package com.revup.user.util;

import com.revup.user.dto.CertificationNumber;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CertificationNumberUtil {

    private static final int NUMBER_LENGTH = 6;
    private static final Random random = new Random();

    public static CertificationNumber generateNumber() {
        StringBuilder sb = new StringBuilder();

        for(int i= 0; i < NUMBER_LENGTH; i++) {
            sb.append(getRandomNumber());
        }
        return new CertificationNumber(sb.toString());
    }

    private static int getRandomNumber() {
        return random.nextInt(10);
    }

}
