package com.jornada.beyondthecodeapi.security;

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

@RequiredArgsConstructor
public class TokenAuthenticatonFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenBearer = request.getHeader("Authorization");
        // Validação de TOKEN
            UsernamePasswordAuthenticationToken tokenSpring = userService.validarToken(tokenBearer);
            SecurityContextHolder.getContext().setAuthentication(tokenSpring); // Logado!

        filterChain.doFilter(request,response);

    }
}
