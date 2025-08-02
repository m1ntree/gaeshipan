package com.example.gaeshipan.controller;

import com.example.gaeshipan.domain.Post;
import com.example.gaeshipan.domain.User;
import com.example.gaeshipan.dto.CreatePostRequest;
import com.example.gaeshipan.service.PostService;
import com.example.gaeshipan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private UserService userService;

    private final PostService postService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/bulletin_board")
    public String showBoard(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("posts", postService.getAllPosts());
        return "bulletin_board";
    }

    @GetMapping("/post/new")
    public String newPost(Model model, Principal principal) {
        if (principal == null){
            return "redirect:/login?reason=not_logged_in";
        }
        model.addAttribute("createPostRequest", new CreatePostRequest());
        return "create_post";
    }

    @PostMapping("/post")
    public String createPost(@Valid @ModelAttribute("post") CreatePostRequest createPostRequest,
                             BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create_post";
        }

        String username = principal.getName();
        User author = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        postService.createPost(createPostRequest.getTitle(), createPostRequest.getContent(), author);
        return "redirect:/bulletin_board";
    }
}
