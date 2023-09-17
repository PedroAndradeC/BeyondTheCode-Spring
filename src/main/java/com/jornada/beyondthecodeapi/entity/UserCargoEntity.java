package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "usuario_cargo")
@Data
public class UserCargoEntity {

    @Id
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_cargo")
    private Integer idCargo;
}
