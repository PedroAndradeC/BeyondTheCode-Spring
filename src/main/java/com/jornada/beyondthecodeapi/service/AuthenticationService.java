package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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
        Optional<UserEntity> usuarioEntityOptional = usuarioService.findByEmail(username);

        if (usuarioEntityOptional.isPresent()){
            return (UserDetails) usuarioEntityOptional.get();
        }

        throw new UsernameNotFoundException("Usuario não encontrado");
    }

}
