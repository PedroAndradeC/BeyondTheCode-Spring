package com.jornada.beyondthecodeapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuarioEntityOptional = usuarioService.findByLogin(username);
        if (usuarioEntityOptional.isPresent()){
            return usuarioEntityOptional.get();
        }throw new UsernameNotFoundException("Usuario não encontrado");
    }

}
