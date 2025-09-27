package com.booklovers.myblogapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Comment content can not be empty")
	@Column(nullable = false)
	private String content;
	
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "post_id",nullable = false)
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

	

	/**
	 * 
	 */
	public Comment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", createdAt=" + createdAt + ", post=" + (post!=null ? post.getId() : null) + ", user="
				+ (user!=null ? user.getId() : null) + "]";
	}
	
	

}
