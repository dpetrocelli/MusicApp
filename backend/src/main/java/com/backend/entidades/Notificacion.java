package com.backend.entidades;

import javax.persistence.*;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    private Long id;
    private String topic;
    private Boolean registrado = false;

    public Notificacion() {
    }

    public Notificacion(Long id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    public Boolean getRegistrado() {
        return registrado;
    }
}
