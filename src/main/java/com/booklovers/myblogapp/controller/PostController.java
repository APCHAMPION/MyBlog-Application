package com.booklovers.myblogapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.booklovers.myblogapp.dto.CommentDto;
import com.booklovers.myblogapp.exception.ResourceNotFoundException;
import com.booklovers.myblogapp.model.Post;
import com.booklovers.myblogapp.service.CommentService;
import com.booklovers.myblogapp.service.PostService;

import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class PostController {
	private final PostService postService;
	private final CommentService commentService;

	/**
	 * @param postService
	 */
	public PostController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping("/")
	public String showHomePage(Model model,
								@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
					    		Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword) {

		Page<Post> postPage;
		
		if (keyword != null && !keyword.isBlank()) {
            // If there's a keyword, call the search service method.
            postPage = postService.searchByTitle(keyword, pageable);
           
            model.addAttribute("keyword", keyword);
        } else {
           
            postPage = postService.findAllPosts(pageable);
            
            
        }

		model.addAttribute("postPage", postPage);
		return "/Home";
	}

	@GetMapping("/post/{id}")
	public String showPostDetailPage(@PathVariable Long id, Model model) {

		Post post = postService.findPostById(id);

		if (post == null) {
			throw new IllegalArgumentException("Invalid post ID:" + id);
		}

		model.addAttribute("post", post);
		model.addAttribute("newComment", new CommentDto());

		return "post-detail";

	}

	@PostMapping("/posts/{postId}/comments")
	public String submitComment(@PathVariable Long postId, @Valid @ModelAttribute("newComment") CommentDto commentDto,
			BindingResult bindingResult, Principal principal, Model model) {

		if (bindingResult.hasErrors()) {
			Post post = postService.findPostById(postId);

			if (post == null) {
				throw new ResourceNotFoundException("Invalid post ID:" + postId);
			}

			model.addAttribute("post", post);

			return "post-detail";
		}
		String username = principal.getName();

		commentService.saveComment(postId, username, commentDto);

		return "redirect:/post/" + postId;
	}

}
