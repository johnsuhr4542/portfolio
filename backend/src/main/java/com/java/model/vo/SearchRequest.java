package com.java.model.vo;

import lombok.Data;

@Data
public class SearchRequest {
	private String searchOption = "all";
	private String searchValue = "";
}
