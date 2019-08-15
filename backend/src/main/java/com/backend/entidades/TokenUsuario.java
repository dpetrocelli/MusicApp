package com.backend.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tokenusuario")

public class TokenUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;

    @Column(unique = true)
    private String token;

    @Column(unique = true)
    private Long expiracion;

    public TokenUsuario() {
    }

    public TokenUsuario(Long idUsuario, String token, Long expiracion) {
        this.idUsuario = idUsuario;
        this.token = token;
        this.expiracion = expiracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }



    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Long expiracion) {
        this.expiracion = expiracion;
    }
}
