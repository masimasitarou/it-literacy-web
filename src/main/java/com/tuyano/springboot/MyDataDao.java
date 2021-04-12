package com.tuyano.springboot;

import java.io.Serializable;
import java.util.List;


//MyDataに対する操作のためのインターフェースをここで用意
public interface MyDataDao<T> extends Serializable{
 public List<T> getAll();
 public T findById(long id);
 public List<T> findByName(String name);
 public List<T> find(String fstr);
 public List<MyData> findAll(long id);
 public T findByTitleId(long id);
}
