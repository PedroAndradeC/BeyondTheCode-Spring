package com.jornada.beyondthecodeapi.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class CommunitysPerTopicEntity {

    @Id
    private String topic;
    private Integer quantidade;



}
