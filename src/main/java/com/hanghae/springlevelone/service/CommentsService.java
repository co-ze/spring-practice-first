package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.CommentsRequestDto;
import com.hanghae.springlevelone.dto.CommentsResponseDto;
import com.hanghae.springlevelone.entity.Comment;
import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.entity.UserOrAdminEnum;
import com.hanghae.springlevelone.repository.CommentsRepository;
import com.hanghae.springlevelone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<Object> createComment(CommentsRequestDto commentsRequestDto, Long id, User user)   {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );

        Comment comment = commentsRepository.saveAndFlush(new Comment(commentsRequestDto, user.getUsername()));
        comment.setPost(post);

        return ResponseEntity.ok().body(new CommentsResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<Object> updateComment(CommentsRequestDto commentsRequestDto, Long id, User user) {

        Comment comment = commentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );

        if (!userCheck(comment, user)) {
            throw new IllegalArgumentException("작성자만 수정/삭제 할 수 있습니다.");
        }
        comment.update(commentsRequestDto);

        return ResponseEntity.ok().body(new CommentsResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<Object> deleteComment(Long id, User user) {

        Comment comment = commentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );

        if (!userCheck(comment, user)) {
            throw new IllegalArgumentException("작성자만 수정/삭제 할 수 있습니다.");
        }
        commentsRepository.delete(comment);

        return ResponseEntity.ok().body("댓글이 삭제 됐습니다.");
    }

    public boolean userCheck(Comment comment, User user) {
        if (!comment.getUsername().equals(user.getUsername())) {
            if (user.getAdmin().equals(UserOrAdminEnum.ADMIN)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}


