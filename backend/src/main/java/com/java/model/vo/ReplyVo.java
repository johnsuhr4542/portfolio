package com.java.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ToString
public class ReplyVo {
	private Long id;
	@NotNull
	private MemberVo author;
	@NotNull
	private ArticleVo article;
	@NotNull
	@Size(max = 2048)
	private String content;
	@JsonIgnore
	private boolean deleted;
	private Date regDate;
	private Date lastModified;
}
