package com.booklovers.myblogapp.dto;

import jakarta.validation.constraints.NotNull;

public class CommentDto {
	@NotNull(message = "Post content can not be empty")
	 private String content;

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

}
