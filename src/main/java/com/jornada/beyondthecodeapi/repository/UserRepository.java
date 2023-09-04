package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.dto.RelatorioUserPostDTO;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

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

}

