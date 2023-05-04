package com.hanghae.springlevelone.dto;

import com.hanghae.springlevelone.entity.Comment;
import lombok.Getter;

@Getter
public class CommentsResponseDto {
    private final String comment;
    private final String username;
    private final int like;

    public CommentsResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.username = comment.getUsername();
        this.like = comment.getCommentLikeList().size();
    }
}
