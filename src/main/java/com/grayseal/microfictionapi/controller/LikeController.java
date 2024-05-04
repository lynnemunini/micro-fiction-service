package com.grayseal.microfictionapi.controller;

import com.grayseal.microfictionapi.model.Like;
import com.grayseal.microfictionapi.model.User;
import com.grayseal.microfictionapi.service.LikeService;
import com.grayseal.microfictionapi.service.StoryService;
import com.grayseal.microfictionapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/likes")
public class LikeController {

    private final StoryService storyService;
    private final UserService userService;
    private final LikeService likeService;

    public LikeController(StoryService storyService, UserService userService, LikeService likeService) {
        this.storyService = storyService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Like> createLike(@RequestBody Like like, Principal principal) {
        if (principal != null && like != null) {
            User user = userService.findUserByEmail(principal.getName());
            if (user != null) {
                if (user.getId().equals(like.getUserId())) {
                    Like createdLike = likeService.createLike(like);
                    if (createdLike != null) {
                        return ResponseEntity.status(HttpStatus.CREATED).body(createdLike);
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}