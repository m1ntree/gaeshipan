package com.example.gaeshipan.service;

import com.example.gaeshipan.domain.Post;
import com.example.gaeshipan.domain.User;
import com.example.gaeshipan.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void createPost(String title, String content, User author){
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setPostDateTime(LocalDateTime.now());
        postRepository.save(post);
    }

    //delete post

    //edit post

}
