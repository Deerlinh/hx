package com.jianq.manager.util.page;

import java.util.List;
import java.util.Map;

/**
 * 分页
 */
public class Page {

	// 默认开始是第一页
	private final static Integer DEFAULT_CURRENT_PAGE_NO = 1;

	// 默认每页大小
	private final static Integer DEFAULT_EVERY_PAGE_SIZE = 20;

	/**
	 * 当前页码(从1开始)
	 */
	private Integer currentPageNo = DEFAULT_CURRENT_PAGE_NO;
	/**
	 * 每页大小(默认为10)
	 */
	private Integer pageSize = DEFAULT_EVERY_PAGE_SIZE;
	/**
	 * 总页数(默认为0，即未统计)
	 */
	private Integer totalPageCount = 0;
	/**
	 * 总记录数(默认为0，即未统计)
	 */
	private Integer totalRecordCount = 0;
	/**
	 * 结果集合
	 */
	private List<?> resultList;

	/**
	 * 该次分页的起始行数(从0开始)
	 */
	private Integer startLineIndex;
	/**
	 * 该次分页的结束行数
	 */
	private Integer endLineIndex;

	/**
	 * 参数map
	 */
	private Map<String, Object> params;

	public Page() {
	}

	/**
	 * 
	 * @param currentPageNo
	 *            当前第几页（从1开始）
	 * @param pageSize
	 *            页大小
	 */
	public Page(Integer currentPageNo, Integer pageSize) {
		this.currentPageNo = currentPageNo;
		this.pageSize = pageSize;
	}

	public Page(Integer currentPageNo, Integer pageSize,
                Map<String, Object> params) {
		this.currentPageNo = currentPageNo;
		this.pageSize = pageSize;
		this.params = params;
	}

	public Integer getCurrentPageNo() {
		// 使分页参数合理化
		if (currentPageNo < 1) {
			this.currentPageNo = 1;
		}
		if (this.getTotalPageCount() != null && this.getTotalPageCount() > 0) {
			if (currentPageNo > this.getTotalPageCount()) {
				this.currentPageNo = this.getTotalPageCount();
			}
		}
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public Integer getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(Integer totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Integer getStartLineIndex() {
		this.startLineIndex = (this.currentPageNo - 1) * this.pageSize;
		return startLineIndex;
	}

	public void setStartLineIndex(Integer startLine) {
		this.startLineIndex = startLine;
	}

	public Integer getEndLineIndex() {
		// 索引 不包含最后一条记录-1
		Integer endIndex = this.getStartLineIndex() + this.getPageSize() - 1;

		if (this.getTotalRecordCount() != null
				&& this.getTotalRecordCount() > 0) {
			if (endIndex > this.getTotalRecordCount()) {
				endIndex = this.getStartLineIndex()
						+ (this.getTotalRecordCount() % this.getPageSize());
			}
		}
		this.endLineIndex = endIndex;
		return endLineIndex;
	}

	public void setEndLineIndex(Integer endLine) {
		this.endLineIndex = endLine;
	}

	@Override
	public String toString() {
		return "Page [currentPageNo=" + currentPageNo + ", pageSize="
				+ pageSize + ", totalPageCount=" + totalPageCount
				+ ", totalRecordCount=" + totalRecordCount + ", resultList="
				+ resultList + ", startLineIndex=" + startLineIndex
				+ ", endLineIndex=" + endLineIndex + ", params=" + params + "]";
	}

}
