package com.ok7.mvvm.poppytvshows.model;

public final class Error {

    private String status_message;
    private int status_code;

    public Error(String status_message) {
        this.status_message = status_message;
    }

    public String getStatusMessage() {
        return status_message;
    }

    public void setStatusMessage(String status_message) {
        this.status_message = status_message;
    }

    public int getStatusCode() {
        return status_code;
    }

    public void setStatusCode(int status_code) {
        this.status_code = status_code;
    }
}
