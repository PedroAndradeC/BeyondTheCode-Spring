package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity(name = "usuario")
@Data
public class UserEntity implements UserDetails {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gerador_user")
    @SequenceGenerator(name = "gerador_user", sequenceName = "usuario_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "senha")
    private String password;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "ativo")
    private Boolean enabled;

    // um user para muitos posts

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private Set<PostEntity> posts;

    @ManyToMany
    @JoinTable(name = "Usuario_Cargo",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_cargo"))
    public Set<CargoEntity> cargos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return cargos;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return enabled; }
}
