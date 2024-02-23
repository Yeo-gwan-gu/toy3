package com.travel.toy3.domain.trip.repository;

import com.travel.toy3.domain.trip.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}