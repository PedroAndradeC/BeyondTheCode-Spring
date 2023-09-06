package com.jornada.beyondthecodeapi.security;

import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class TokenAuthenticatonFilter extends OncePerRequestFilter {
//
    public final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenBearer = request.getHeader("Authorization");
//        // Validação de TOKEN
        try {
            UserEntity user = userService.validarToken(tokenBearer);
            UsernamePasswordAuthenticationToken tokenSpring
                    = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(tokenSpring);
        } catch (RegraDeNegocioException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
//            UsernamePasswordAuthenticationToken tokenSpring = userService.validarToken(tokenBearer);
//            SecurityContextHolder.getContext().setAuthentication(tokenSpring); // Logado!

        filterChain.doFilter(request,response);

    }
}
