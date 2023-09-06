package com.jornada.beyondthecodeapi.security;

import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> usuarioEntityOptional = userService.findByEmail(username);

        if (usuarioEntityOptional.isPresent()){
            return usuarioEntityOptional.get();
        }
//
        throw new UsernameNotFoundException("Usuario não encontrado");
    }

}
