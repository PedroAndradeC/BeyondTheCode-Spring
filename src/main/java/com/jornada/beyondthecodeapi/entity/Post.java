package com.jornada.beyondthecodeapi.entity;

import lombok.Data;

@Data
public class Post {
    private String contents;
    private String title;
    private Integer idPost;
    private Integer idUser;
}
