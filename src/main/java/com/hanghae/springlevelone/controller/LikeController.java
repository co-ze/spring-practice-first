package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.security.UserDetailsImpl;
import com.hanghae.springlevelone.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog/like")
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/post/{id}")
    public ResponseEntity<String> pushPostLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.pushPostLike(id, userDetails.getUser());
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<String> pushCommentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.pushCommentLike(id, userDetails.getUser());
    }
}
