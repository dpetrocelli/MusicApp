package com.backend.dto;

import java.util.Date;

public class PagoMercadoPago {
    private final String resource;
    private final String user_id;
    private final String topic;
    private final int attempts;
    private final Date sent;
    private final Date recieved;

    public PagoMercadoPago(String resource, String user_id, String topic, int attempts, Date sent, Date recieved) {
        this.resource = resource;
        this.user_id = user_id;
        this.topic = topic;
        this.attempts = attempts;
        this.sent = sent;
        this.recieved = recieved;
    }

    public String getResource() {
        return resource;
    }

    public String getId() {
        return resource.replace("/collections/","");
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTopic() {
        return topic;
    }

    public int getAttempts() {
        return attempts;
    }

    public Date getSent() {
        return sent;
    }

    public Date getRecieved() {
        return recieved;
    }
}
