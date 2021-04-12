package com.tuyano.springboot;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "urldata")
public class UrlData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	@CsvBindByName(column = "PRIMARY_ID")
	private long prId;

	@NotNull
	@CsvBindByName(column = "TITLE_ID")
	private long id;
	
	@Column(nullable = true)
	@CsvBindByName(column = "URL")
	private String url;

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getPrId() {
		return prId;
	}

	public void setPrId(long prId) {
		this.prId = prId;
	}

}
