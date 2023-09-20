package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "comunidade")
@Getter
@Setter
public class CommunityEntity {
//    @Field(targetType = FieldType.OBJECT_ID)
    @Id
    private String idCommunity;

    private String nameCommunity;

    private String descriptionCommunity;

//    @Column(name = "membros_comunidade")
//    private String membersCommunity;

//    private Date creationDate;

}
