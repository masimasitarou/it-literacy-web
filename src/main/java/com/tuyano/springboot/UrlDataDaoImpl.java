package com.tuyano.springboot;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.tuyano.springboot.repositries.UrlDataDao;

@SuppressWarnings("rawtypes")
@Repository
public class UrlDataDaoImpl implements UrlDataDao<UrlDataDao> {
	
	private EntityManager entityManager;
	
	public UrlDataDaoImpl() {
		super();
	}
	
	public UrlDataDaoImpl(EntityManager manager) {
		this();
		// TODO Auto-generated constructor stub
		entityManager = manager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UrlData> getAll() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from UrlData").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<UrlData> findAll(long id) {
		return entityManager.createQuery("from UrlData where id = " + id).getResultList();
	}
	
	@Override
	public UrlData findById(long id) {
		// TODO Auto-generated method stub
		return (UrlData)entityManager.createQuery("from UrlData where id = " + id).getSingleResult();
	}
	
	/*
	@Override
	public UrlData deleteAllById(long id) {
		// TODO Auto-generated method stub
		return (UrlData)entityManager.createQuery("delete from UrlData where id = " + id);
	}*/

}
