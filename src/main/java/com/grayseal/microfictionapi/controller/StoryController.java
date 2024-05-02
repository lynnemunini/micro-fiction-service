package com.grayseal.microfictionapi.controller;

import com.grayseal.microfictionapi.model.Story;
import com.grayseal.microfictionapi.model.User;
import com.grayseal.microfictionapi.service.StoryService;
import com.grayseal.microfictionapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    public StoryController(StoryService storyService, UserService userService) {
        this.storyService = storyService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Story> createStory(@RequestBody Story story, Principal principal) {
        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            if (user.getId().equals(story.getUserId())) {
                Story createdStory = storyService.createStory(story);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdStory);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Story> findStoryById(@PathVariable Long storyId) {
        // Implement the findStoryById method in the service and return the ResponseEntity accordingly
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Story>> findAllUserStories(@PathVariable Long userId) {
        // Implement the findAllUserStories method in the service and return the ResponseEntity accordingly
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Story>> findAllStories() {
        // Implement the findAllStories method in the service and return the ResponseEntity accordingly
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{storyId}")
    public ResponseEntity<Story> updateStory(@PathVariable Long storyId, @RequestBody Story updatedStory) {
        // Implement the updateStory method in the service and return the ResponseEntity accordingly
        return ResponseEntity.status(HttpStatus.OK).body(updatedStory);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long storyId) {
        // Implement the deleteStory method in the service and return the ResponseEntity accordingly
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
