package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.entity.UserCargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCargoRepository extends JpaRepository<UserCargoEntity, Integer> {
}
