package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Set;

@Entity(name = "usuario")
@Data
public class UserEntity {
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

    // um user para muitos posts
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private Set<PostEntity> postEntities;

}
