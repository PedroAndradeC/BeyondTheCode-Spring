package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.entity.CommunityEntity;
import com.jornada.beyondthecodeapi.entity.CommunitysPerTopicEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends MongoRepository<CommunityEntity, String> {

    @Aggregation(pipeline = {
            "{$match: {nameCommunity: /.*?0.*/i}}",
            "{$group: {'_id': '$communityTopic', contagem: {$sum: 1}} }",
            "{$project: {'topic': '_id', 'quantidade': '$contagem'}}",
            "{$sort: {'topic':1 }}"
    })
    List<CommunitysPerTopicEntity> findByCommunityTopics (String topic);


}
