package com.booklovers.myblogapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booklovers.myblogapp.exception.ResourceNotFoundException;
import com.booklovers.myblogapp.model.Post;
import com.booklovers.myblogapp.model.User;
import com.booklovers.myblogapp.repository.PostRepository;
import com.booklovers.myblogapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	/**
	 * @param postRepository
	 */
	public PostService(PostRepository postRepository,UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	
	public Page<Post> findAllPosts(Pageable pageable){
		
		return postRepository.findAll(pageable);
	}
	
	public List<Post> findAllPosts(){
		
		return postRepository.findAll();
	}
	
	public Page<Post> searchByTitle(String keyword, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }
	
	
	public Post findPostById(Long id) {
		
		return postRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("post not found by id: "+id));
	}
	
	@Transactional // Ensures the operations are a single atomic transaction
    public Post savePost(Post post, String username) {
        
        if (post.getId() == null) { 
            
            User author = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("Cannot find user with username: " + username));
            
          
            post.setUser(author);
            
           
            post.setCreatedAt(LocalDateTime.now());
        }else {
            
            Post existingPost = postRepository.findById(post.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + post.getId()));

           
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());

           
            return postRepository.save(existingPost);
        }
       
        return postRepository.save(post);
    }
	
	public void deletePostById(Long id) {
		postRepository.deleteById(id);
	}

}
