package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Set;

@Document(collection = "comunidade")
@Data
public class CommunityEntity {
    @Field(name = "id")
    @Id
    private String idCommunity;

    private String nameCommunity;

    private String descriptionCommunity;

//    @Column(name = "membros_comunidade")
//    private String membersCommunity;

//    private Date creationDate;
}
