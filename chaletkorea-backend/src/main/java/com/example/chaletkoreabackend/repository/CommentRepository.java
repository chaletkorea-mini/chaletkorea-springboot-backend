package com.example.chaletkoreabackend.repository;

import com.example.chaletkoreabackend.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
