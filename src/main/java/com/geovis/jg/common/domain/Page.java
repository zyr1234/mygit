package com.geovis.jg.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对应实体类
 * @author NPF
 *
 */
public class Page implements Serializable{


    private Integer page;
    private Object rows;
    private String sort;
    private String order;
    private String columns;
    private long total;
    private List content;
    private Integer pageNum;

    private Integer start;
    private Integer length;

    public List getContent() {
		return content;
	}

	public void setContent(List content) {
		this.content = content;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
        this.page = start;
    }

    public void setLength(Integer length) {
        this.length = length;
        this.rows = length;
    }

    public long getRecordsTotal(){
        return total;
    }
    public long getRecordsFiltered(){
        return total;
    }
}
