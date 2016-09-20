package com.trademarket.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trademarket.entity.CodeList;


public interface CodeListJPARepo extends JpaRepository<CodeList, Integer>{
	
	public List<CodeList> findByClGroup(String clGroup);

}
