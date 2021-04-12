package com.tuyano.springboot;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import com.opencsv.bean.CsvBindByName;
import javax.persistence.CascadeType;

@Entity
@Table(name="mydata")
public class MyData {
	
	//@OneToMany(mappedBy = "id",fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	//private List<UrlData> urlDatas;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@CsvBindByName(column = "PRIMARY_ID")
	@NotNull
	private long prId;
	
	@CsvBindByName(column = "TITLE_ID")
	@NotNull
	@Column(name = "titleId",nullable = false)
	private long id;
		
	@Column(length = 50, nullable = false)
	@NotEmpty(message = "空白は不可です。")
	@CsvBindByName(column = "TITLE")
	private String title;

	@Column(nullable = false)
	@CsvBindByName(column = "MEMBER_MEMO")
	private String memo;
	
	public long getPrId() {
		return prId;
	}

	public void setPrId(long prId) {
		this.prId = prId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
