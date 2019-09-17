package com.gpmall.search.constant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Description: 查询分页对象
 * @Author： wz
 * @Date: 2019-08-24 19:18
 **/
public class PageInfo implements Pageable {
  private int pageNumber;
  private int pageSize;
  private long offset;
  private  Sort sort;

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}
}