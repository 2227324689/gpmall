package com.gpmall.search.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author jin
 */

@Document(indexName = "tb_item", type = "doc", shards = 1, replicas = 0)
public class ItemDocument {
	@Id
	private Integer id;
	@Field(type = FieldType.Text)
	private String image;
	@Field(type = FieldType.Long)
	private Integer status;
	@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer ="ik_max_word" )
	private String sell_point;
	@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer ="ik_max_word")
	private String title;
	@Field(type = FieldType.Long)
	private Integer num;
	@Field(type = FieldType.Long)
	private Long cid;
	@Field(type = FieldType.Date)
	private Date created;
	@Field(type = FieldType.Float)
	private Double price;
	@Field(type = FieldType.Long)
	private Integer limit_num;
	@Field(type = FieldType.Date)
	private Date updated;

	public void setCid(Long cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "ItemDocument{" +
				"id=" + id +
				", image='" + image + '\'' +
				", status=" + status +
				", sell_point='" + sell_point + '\'' +
				", title='" + title + '\'' +
				", num=" + num +
				", cid=" + cid +
				", created=" + created +
				", price=" + price +
				", limit_num=" + limit_num +
				", updated=" + updated +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSell_point() {
		return sell_point;
	}

	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getLimit_num() {
		return limit_num;
	}

	public void setLimit_num(Integer limit_num) {
		this.limit_num = limit_num;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
