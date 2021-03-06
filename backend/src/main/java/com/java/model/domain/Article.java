package com.java.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
@Data
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author", nullable = false)
	private Member author;

	@Column(length = 128, nullable = false)
	private String title;

	@Column(length = 4096, nullable = false)
	private String content;

	@Column(columnDefinition = "tinyint(1) default 0",
		insertable = false, nullable = false)
	private boolean deleted;

	@Column(columnDefinition = "date default CURRENT_TIMESTAMP",
		insertable = false, updatable = false, nullable = false)
	private Date regDate;

	@Column(columnDefinition = "date default CURRENT_TIMESTAMP",
		insertable = false, nullable = false)
	private Date lastModified;

}
