package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.CommentsResponseDto;
import com.hanghae.springlevelone.dto.CommentsRequestDto;
import com.hanghae.springlevelone.entity.Comment;
import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.repository.CommentsRepository;
import com.hanghae.springlevelone.repository.PostRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto, Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            return null;
        }
        if (!jwtUtil.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            return null;
        }
        claims = jwtUtil.getInfoFromToken(token);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );

        Comment comment = commentsRepository.saveAndFlush(new Comment(commentsRequestDto, claims.getSubject()));
        comment.setPost(post);

        return new CommentsResponseDto(comment);
    }

    @Transactional
    public CommentsResponseDto updateComment(CommentsRequestDto commentsRequestDto, Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            return null;
        }
        if (!jwtUtil.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            return null;
        }

        claims = jwtUtil.getInfoFromToken(token);
        Comment comment = commentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );

        if (!comment.getUsername().equals(claims.getSubject())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "작성자만 수정할 수 있습니다.");
            return null;
        }
        comment.update(commentsRequestDto);

        response.setStatus(HttpServletResponse.SC_OK);
        return new CommentsResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            return null;
        }
        if (!jwtUtil.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            return null;
        }

        claims = jwtUtil.getInfoFromToken(token);
        Comment comment = commentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );

        if (!comment.getUsername().equals(claims.getSubject())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "작성자만 삭제할 수 있습니다.");
            return null;
        }
        commentsRepository.delete(comment);

        response.setStatus(HttpServletResponse.SC_OK);
        return HttpStatus.OK.value()+" 댓글이 삭제됐습니다.";
    }
}

