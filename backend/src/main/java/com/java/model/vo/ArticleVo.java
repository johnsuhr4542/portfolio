package com.java.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {
	private Long id;
	@NotNull
	private MemberVo author;
	@NotNull
	@Size(max = 128)
	private String title;
	@NotNull
	@Size(max = 4096)
	private String content;
	@JsonIgnore
	private boolean deleted;
	private Date regDate;
	private Date lastModified;
	private List<ReplyVo> replies;
}
