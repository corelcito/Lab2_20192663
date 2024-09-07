package com.juegoantena;

import java.io.Serializable;

public class GameResult implements Serializable {
    private final long timeInSeconds;
    private final ResultType resultType;

    public GameResult(long timeInSeconds, ResultType resultType) {
        this.timeInSeconds = timeInSeconds;
        this.resultType = resultType;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public ResultType getResultType() {
        return resultType;
    }
}