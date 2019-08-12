package com.java.model.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "member")
@Data
public class Member {

	@Id
	@Column(length = 24, nullable = false)
	private String username;

	@Column(length = 80, nullable = false)
	private String password;

	@Column(length = 64, nullable = false)
	private String email;

	@Column(columnDefinition = "varchar(24) default 'ROLE_USER'",
		insertable = false, nullable = false)
	private String authority;

	@Column(columnDefinition = "date default CURRENT_TIMESTAMP",
		insertable = false, updatable = false, nullable = false)
	private Date regDate;

	@Column(columnDefinition = "date default CURRENT_TIMESTAMP",
		insertable = false, nullable = false)
	private Date lastModified;

	@Column(columnDefinition = "tinyint(1) default 0",
		insertable = false, nullable = false)
	private boolean deleted;

}
