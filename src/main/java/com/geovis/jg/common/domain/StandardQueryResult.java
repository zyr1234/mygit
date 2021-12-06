package com.geovis.jg.common.domain;

import java.io.Serializable;
import java.util.Arrays;

//@CacheConfig(cacheNames="ssss")
public class StandardQueryResult implements Serializable
{
	/** 数据ID */
	public String id = "";
	
	/** 数据名称 */
	public String name = "";
	
	/** 数据中文名称 */
	public String chname = "";
	
	/** 数据描述 */
	public String description = "";

	/** 数据类型 */
	public String type = "";
	
	/** 数据Tags */
	public String[] tags = new String[0];
	
	/** 数据时间 */
	public String date = "";
	
	/** 数据备记 [一些现有字段无法存储的内容] */
	public String remark = "";
	
	/** 原始返回数据对象 */
	public Object data;

	/** 数据链接地址[可以跳转到数据] */
	public String url = "";
	
	/** 数据原信息链接地址 */
	public String metadata = "";

	public StandardQueryResult() {
	}

	public StandardQueryResult(String id, String name, String chname, String description, String type, String[] tags,
                               String date, String remark, Object data, String url, String metadata) {
		super();
		this.id = id;
		this.name = name;
		this.chname = chname;
		this.description = description;
		this.type = type;
		this.tags = tags;
		this.date = date;
		this.remark = remark;
		this.data = data;
		this.url = url;
		this.metadata = metadata;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChname() {
		return chname;
	}

	public void setChname(String chname) {
		this.chname = chname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "StandardQueryResult [id=" + id + ", name=" + name + ", chname=" + chname + ", description="
				+ description + ", type=" + type + ", tags=" + Arrays.toString(tags) + ", date=" + date + ", remark="
				+ remark + ", data=" + data + ", url=" + url + ", metadata=" + metadata + "]";
	}
	
	
}
