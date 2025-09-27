package com.booklovers.myblogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booklovers.myblogapp.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
