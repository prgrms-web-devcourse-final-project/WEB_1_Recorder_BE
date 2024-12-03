package com.revup.heart.enums;

import com.revup.heart.exception.InvalidHeartTypeException;

public enum HeartType {
    GOOD,BAD;

    public static HeartType of(String type){
        try {
            return HeartType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new InvalidHeartTypeException();
        }
    }

    public boolean isGoodType(){
        return HeartType.GOOD == this;
    }

}
