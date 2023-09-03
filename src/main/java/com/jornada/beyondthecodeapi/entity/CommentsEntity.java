package com.jornada.beyondthecodeapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Comments")
@Data
public class CommentsEntity {
    @Id
    @Column(name = "id_comment")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gerador_comments")
    @SequenceGenerator(name = "gerador_comments", sequenceName = "comments_seq", allocationSize = 1)
    private Integer idComment;
    @Column(name = "conteudo")
    private String contents;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post", referencedColumnName = "id_post")
    private PostEntity postEntity;
}
