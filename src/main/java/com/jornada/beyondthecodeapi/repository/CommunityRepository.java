package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.entity.CommunityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends MongoRepository<CommunityEntity, Integer> {
}
