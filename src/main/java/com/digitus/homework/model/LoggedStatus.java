package com.digitus.homework.model;

public enum LoggedStatus {
    LOGGED_IN(1),
    LOGGED_OUT(0);

    public final int value;

    private LoggedStatus(int value) {
        this.value = value;
    }
}
