package com.joaquin.hackathon.forapi;
//API response will be converted into this class' object
public class WhatsAppModel {
    private String message;
    private String status;
    private String request_id;

    public WhatsAppModel(String message, String status, String request_id) {
        this.message = message;
        this.status = status;
        this.request_id = request_id;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
