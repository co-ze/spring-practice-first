package com.hanghae.springlevelone.repository;

import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.entity.PostLike;
import com.hanghae.springlevelone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByUserIdAndPostId(Long user_id, Long post_id);
    void deleteLikeByUserAndPost(User user, Post post);
}
