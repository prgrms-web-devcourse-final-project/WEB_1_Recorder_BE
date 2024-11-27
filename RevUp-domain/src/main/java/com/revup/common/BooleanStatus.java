package com.revup.common;

public enum BooleanStatus {
    TRUE,
    FALSE;

    public static BooleanStatus from(boolean value){
        return value ? TRUE : FALSE;
    }

    public boolean toBoolean(){
        return this == TRUE;
    }
}
