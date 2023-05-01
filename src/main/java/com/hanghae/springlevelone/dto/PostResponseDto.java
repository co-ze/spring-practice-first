package com.hanghae.springlevelone.dto;

import com.hanghae.springlevelone.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String username;
    private final String post;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private List<CommentsResponseDto> comments;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.post = post.getPost();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.comments = post.getCommentsList().stream().map(CommentsResponseDto::new).collect(Collectors.toList());
    }
}
