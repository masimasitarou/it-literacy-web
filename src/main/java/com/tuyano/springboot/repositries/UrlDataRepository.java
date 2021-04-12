package com.tuyano.springboot.repositries;

import java.util.List;
import java.util.Optional;

import com.tuyano.springboot.MyData;
import com.tuyano.springboot.UrlData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlDataRepository extends JpaRepository<UrlData, Long> {
	public Optional<UrlData> findById(Long name);
	//public UrlData deletebyData(Long id);
	//@Query("SELECT DISTINCT e FROM Equipment e INNER JOIN e.room WHERE e.room.roomId = :roomId ORDER BY e.equipmentId")
    //List<MyData> find(@Param("id") long id);
}
