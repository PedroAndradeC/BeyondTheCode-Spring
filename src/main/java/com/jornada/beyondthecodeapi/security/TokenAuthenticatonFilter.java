package com.jornada.beyondthecodeapi.security;

import com.jornada.beyondthecodeapi.controller.UserController;
import com.jornada.beyondthecodeapi.entity.User;
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

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenBearer = request.getHeader("Authorization");
        // Validação de TOKEN
        try {
            User userEntity = userService.validarToken(tokenBearer);
            UsernamePasswordAuthenticationToken tokenSpring = new UsernamePasswordAuthenticationToken(userEntity,
                    null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(tokenSpring); // Logado!
        } catch (RegraDeNegocioException e){
            SecurityContextHolder.getContext().setAuthentication(null); // Não está logado!
        }

        filterChain.doFilter(request,response);

    }
}
