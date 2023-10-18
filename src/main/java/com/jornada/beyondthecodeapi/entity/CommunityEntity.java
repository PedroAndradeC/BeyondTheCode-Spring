package com.jornada.beyondthecodeapi.entity;

import com.jornada.beyondthecodeapi.dto.OperacaoCommunity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comunidade")
@Getter
@Setter
public class CommunityEntity {
//    @Field(targetType = FieldType.OBJECT_ID)
    @Id
    private String idCommunity;

    private String nameCommunity;

    private String communityTopic;

    private OperacaoCommunity operacaoCommunity;

//    @Column(name = "membros_comunidade")
//    private String membersCommunity;

//    private Date creationDate;

}
