package com.jornada.beyondthecodeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity(name = "Post")
@Data
public class PostEntity {
    @Id
    @Column(name = "id_post")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gerador_post")
    @SequenceGenerator(name = "gerador_post", sequenceName = "post_seq", allocationSize = 1)
    private Integer idPost;
    @Column(name = "titulo")
    private String title;
    @Column(name = "conteudo")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private UserEntity userEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<CommentsEntity> comments;
}

