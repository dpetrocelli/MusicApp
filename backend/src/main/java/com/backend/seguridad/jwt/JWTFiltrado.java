package com.backend.seguridad.jwt;

import com.backend.servicios.UsuarioDetallesServicioPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFiltrado extends OncePerRequestFilter {

    private static  final Logger logger = LoggerFactory.getLogger(JWTFiltrado.class);

    @Autowired
    JWTProveedor JWPProveedor;

    @Autowired
    UsuarioDetallesServicioPrincipal usuarioDetallesServicio;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(req);
            if(token !=null && JWPProveedor.validateToken(token)){
                String nombreUsuario = JWPProveedor.getNombreUsuarioFromToken(token);
                UserDetails userDetails = usuarioDetallesServicio.loadUserByUsername(nombreUsuario);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            logger.error("failétodo doFilter " + e.getMessage());
        }
        filterChain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest request){
        String authReq = request.getHeader("Authorization");
        if(authReq != null && authReq.startsWith("Bearer "))
            return authReq.replace("Bearer ", "");
        return null;
    }
}


