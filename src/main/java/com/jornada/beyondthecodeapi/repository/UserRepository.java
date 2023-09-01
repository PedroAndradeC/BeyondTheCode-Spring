package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.dto.RelatorioUserPostDTO;
import com.jornada.beyondthecodeapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("      Select new com.jornada.beyondthecodeapi.dto.RelatorioUserPostDTO(u.id," +
            "           u.name," +
            "           p.idPost," +
            "           p.title," +
            "           c.idComment," +
            "           c.contents)" +
            "     from usuario u " +
            "     left join u.posts p " +
            "     left join p.comments c ")
    List<RelatorioUserPostDTO> buscarUserPostEComments();

    Optional<User> findByLoginAndSenha(String login, String senha);

}

