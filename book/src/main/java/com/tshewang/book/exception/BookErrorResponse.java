package com.tshewang.book.exception;

public class BookErrorResponse {
    private String message;
    private int status;
    private long timeStamp;

    public BookErrorResponse(String message, int status, long timeStamp) {
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
