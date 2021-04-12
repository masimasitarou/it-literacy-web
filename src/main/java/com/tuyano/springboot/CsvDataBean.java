package com.tuyano.springboot;

import com.opencsv.bean.CsvBindByName;

public class CsvDataBean {
     @CsvBindByName(column = "MEMBER_NAME")
     private String memberName;

     @CsvBindByName(column = "MEMBER_MAIL")
     private String memberMail;
     
     @CsvBindByName(column = "MEMBER_AGE")
     private String memberAge;
     
     @CsvBindByName(column = "MEMBER_MEMO")
     private String memberMemo;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberMail() {
		return memberMail;
	}

	public void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}

	public String getMemberAge() {
		return memberAge;
	}

	public void setMemberAge(String memberAge) {
		this.memberAge = memberAge;
	}

	public String getMemberMemo() {
		return memberMemo;
	}

	public void setMemberMemo(String memberMemo) {
		this.memberMemo = memberMemo;
	}

     
}
