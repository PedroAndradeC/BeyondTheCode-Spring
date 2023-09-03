package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Integer> {
}
