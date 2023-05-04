package com.hanghae.springlevelone.repository;

import com.hanghae.springlevelone.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserIdAndCommentId(Long user_id, Long comment_id);
    void deleteLikeByUserAndComment(User user, Comment comment);
}
