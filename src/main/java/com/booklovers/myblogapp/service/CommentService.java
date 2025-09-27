package com.booklovers.myblogapp.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.booklovers.myblogapp.dto.CommentDto;
import com.booklovers.myblogapp.exception.ResourceNotFoundException;
import com.booklovers.myblogapp.model.Comment;
import com.booklovers.myblogapp.model.Post;
import com.booklovers.myblogapp.model.User;
import com.booklovers.myblogapp.repository.CommentRepository;
import com.booklovers.myblogapp.repository.PostRepository;
import com.booklovers.myblogapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
	
	 private final CommentRepository commentRepository;
	    private final PostRepository postRepository;
	    private final UserRepository userRepository;

	    // Inject all required repositories via the constructor
	    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
	        this.commentRepository = commentRepository;
	        this.postRepository = postRepository;
	        this.userRepository = userRepository;
	    }

	   
	    @Transactional // Ensures this entire operation is a single database transaction
	    public void saveComment(Long postId, String username, CommentDto commentDto) {
	        Post post = postRepository.findById(postId)
	                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

	        Comment comment = new Comment();
	        comment.setContent(commentDto.getContent());
	        comment.setCreatedAt(LocalDateTime.now());
	        comment.setPost(post); // Associate the comment with the post
	        comment.setUser(user); // Associate the comment with the user (author)

	        commentRepository.save(comment);
	
	    }

}
