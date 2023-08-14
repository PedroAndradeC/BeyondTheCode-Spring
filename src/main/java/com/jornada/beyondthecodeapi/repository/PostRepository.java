package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
