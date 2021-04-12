package com.tuyano.springboot.repositries;

import java.util.List;

import com.tuyano.springboot.UrlData;

//URLのデータに対する操作のためのインターフェースをここで用意
public interface UrlDataDao<T> {
	public List<UrlData> getAll();
	public List<UrlData> findAll(long id);
	public UrlData findById(long id);
}
