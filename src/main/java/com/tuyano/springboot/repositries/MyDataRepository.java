package com.tuyano.springboot.repositries;

import com.tuyano.springboot.MyData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

//MyDataにアクセスするためのインターフェースをここで用意。
@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {
	public Optional<MyData> findById(Long name);
	//public MyData findByTitleId(long id);
}

