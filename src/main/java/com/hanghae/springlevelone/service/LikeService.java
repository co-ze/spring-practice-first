package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.entity.*;
import com.hanghae.springlevelone.repository.CommentLikeRepository;
import com.hanghae.springlevelone.repository.CommentsRepository;
import com.hanghae.springlevelone.repository.PostLikeRepository;
import com.hanghae.springlevelone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentsRepository commentsRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ResponseEntity<String> pushPostLike(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        PostLike postLike = postLikeRepository.findByUserIdAndPostId(user.getId(), id);
        if (postLike == null) {
            postLikeRepository.save(new PostLike(post, user));
            return ResponseEntity.ok().body("게시글 좋아요!");
        } else {
            postLikeRepository.deleteLikeByUserAndPost(user, post);
            return ResponseEntity.ok().body("게시글 좋아요 취소");
        }
    }

    @Transactional
    public ResponseEntity<String> pushCommentLike(Long id, User user) {
        Comment comment = commentsRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );
        CommentLike commentLike = commentLikeRepository.findByUserIdAndCommentId(user.getId(), id);
        if (commentLike == null) {
            commentLikeRepository.save(new CommentLike(comment, user));
            return ResponseEntity.ok().body("댓글 좋아요!");
        } else {
            commentLikeRepository.deleteLikeByUserAndComment(user, comment);
            return ResponseEntity.ok().body("댓글 좋아요 취소");
        }
    }
}
