package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity(name="Cargo")
public class CargoEntity {

    @Id
    @Column(name="id_cargo")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "seq_cargo", allocationSize = 1)
    private Integer idCargo;

    @Column(name = "nome")
    private String nome;

    @ManyToMany
    @JoinTable(name = "Usuario_Cargo",
    joinColumns = @JoinColumn(name = "id_cargo"),
    inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    public Set<UserEntity> usuarios;


}
