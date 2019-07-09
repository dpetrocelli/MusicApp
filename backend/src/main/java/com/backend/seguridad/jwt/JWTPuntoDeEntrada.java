package com.backend.seguridad.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTPuntoDeEntrada implements AuthenticationEntryPoint {

    private static  final Logger logger = LoggerFactory.getLogger(JWTPuntoDeEntrada.class);
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        logger.error("faill método commence");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "credenciales erróneas");
    }
}

