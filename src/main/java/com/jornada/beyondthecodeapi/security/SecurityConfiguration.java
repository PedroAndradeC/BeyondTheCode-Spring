package com.jornada.beyondthecodeapi.security;

import com.jornada.beyondthecodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    public final UserService userService;

    @Bean // Filtro de requisições
    public SecurityFilterChain filterChain(HttpSecurity  http)throws Exception{

        // Configuração de permissões/filtros
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        // Permissão de acesso ao "/autenticação"
        http.authorizeHttpRequests((authz)->
                        authz.requestMatchers("/autenticacao/**").permitAll()
                        .anyRequest().authenticated());
        // Filtro de autenticação ao Token
        http.addFilterBefore(new TokenAuthenticatonFilter(userService), UsernamePasswordAuthenticationFilter.class);

       return http.build(); // Retorna a http (requisição)
    }

    // Liberação de acesso do Swagger
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(
                "/v3/api-docs",
                "v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/");
    }

}
