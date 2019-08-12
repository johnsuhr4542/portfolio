package com.java.model.util;

import lombok.ToString;

@ToString
public class Pagination {

	private int pageSize;
	private int blockSize = 10;

	private int totalThread;

	private int page;
	private int totalPage;
	private int prevPage;
	private int nextPage;

	private int block;
	private int totalBlock;
	private int blockBegin;
	private int blockEnd;

	public Pagination(int page, int pageSize, int totalThread) {
		if (page < 1) page = 1;
		this.page = page;
		this.pageSize = pageSize;
		this.totalThread = totalThread;
		calcaulate();
	}

	private void calcaulate() {
		totalPage = (int) Math.ceil( ( totalThread * 1.0 ) / pageSize );
		if (totalPage < 1) totalPage = 1;
		totalBlock = (int) Math.ceil( ( totalPage * 1.0 ) / blockSize );

		block = ( page - 1 ) / blockSize + 1;
		blockBegin = ( block - 1 ) * blockSize + 1;
		if (blockBegin < 1) blockBegin = 1;
		blockEnd = blockBegin + blockSize - 1;
		if (blockEnd > totalPage) blockEnd = totalPage;

		prevPage = blockBegin - 1;
		if (prevPage < 1) prevPage = 1;
		nextPage = blockEnd + 1;
		if (nextPage > totalPage) nextPage = totalPage;
	}

	public int getPage() {
		return this.page;
	}

	public int getPrevPage() {
		return this.prevPage;
	}

	public int getNextPage() {
		return this.nextPage;
	}

	public int getBlockBegin() {
		return this.blockBegin;
	}

	public int getBlockEnd() {
		return this.blockEnd;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public int getTotalBlock() {
		return this.totalBlock;
	}

	public boolean isFirstBlock() {
		return block == 1;
	}

	public boolean isLastBlock() {
		return block == totalBlock;
	}

}
