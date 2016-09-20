package com.app.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.trademarket.model.CodeListModel;
import com.trademarket.model.EnrollModel;
import com.trademarket.model.SearchModel;
import com.trademarket.service.EnrollService;
import com.trademarket.utils.CodeListGroup;



@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration(classes={JPAConfigTest.class}, 
		loader=AnnotationConfigContextLoader.class)
public class RepoTest {
	
	
	@Autowired
	EnrollService service;
	
	
	
	/*@Test
	public void saveTest(){
		
		EnrollModel enroll=new EnrollModel();
		
		enroll.setCountry(1);
		enroll.setName("bharath");
		enroll.setOrgName("Raagaa");
		
		service.saveEnroll(enroll);
		
		CodeListModel codeList=new CodeListModel();
		
		codeList.setClGroup(CodeListGroup.COUNTRY.toString());
		codeList.setClLabel("INDIA");
		codeList.setCodeId(1);
		codeList.setClOrder(1);
		codeList.setClAlternate("Indian");
		codeList.setClFlag(1);
		
		
		service.saveCodeList(codeList);
			
	}*/
	
	@Test
	public void getListTest(){
		SearchModel search=new SearchModel();
		service.getDashBoardChart(search);
	}
	
	
	
}
