package com.backend.wrappers;

import com.backend.entidades.Post;
import com.backend.recursos.LoginDatos;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostRequest {
    @JsonProperty("login")
    LoginDatos loginDatos;
    @JsonProperty("post")
    Post post;

    public PostRequest(LoginDatos loginDatos, Post post) {
        this.loginDatos = loginDatos;
        this.post = post;
    }

    public LoginDatos getLoginDatos() {
        return loginDatos;
    }

    public Post getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "LoginDatos: "+this.loginDatos.getNombreUsuario()+"\nPost: "+this.post.getInformacion();
    }
}
