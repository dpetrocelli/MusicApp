package com.backend.recursos;

public class AccessTokenMP {
    String client_id;
    String client_secret;
    String grant_type;
    String code;
    String redirect_uri;

    public AccessTokenMP(String client_id, String client_secret, String grant_type, String code, String redirect_uri) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
        this.code = code;
        this.redirect_uri = redirect_uri;
    }

}
