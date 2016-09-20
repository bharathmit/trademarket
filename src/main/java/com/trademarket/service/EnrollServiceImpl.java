package com.trademarket.service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trademarket.entity.CodeList;
import com.trademarket.entity.Enroll;
import com.trademarket.model.ChartModel;
import com.trademarket.model.CodeListModel;
import com.trademarket.model.EnrollModel;
import com.trademarket.model.SearchModel;
import com.trademarket.repo.CodeListJPARepo;
import com.trademarket.repo.EnrollJPARepo;
import com.trademarket.utils.ModelEntityMapper;



@Service
public class EnrollServiceImpl implements EnrollService{
	
	private static Logger log = Logger.getLogger(EnrollServiceImpl.class);
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	EnrollJPARepo enrollRepo;
	
	@Autowired
	CodeListJPARepo codeListRepo;
	
	@Autowired
	VelocityEngine velocityEngine;
	
	
	@Override
	@Transactional
	public Long saveEnroll(EnrollModel enroll) {
		try{
			Enroll enrollEntity=(Enroll) ModelEntityMapper.converModelToEntity(enroll, Enroll.class);
			enrollRepo.saveAndFlush(enrollEntity);
			return enrollEntity.getId();
		}
		catch(Exception e){
			e.printStackTrace();
			return 0l;
		}
	}

	
	@Override
	@Transactional
	public Boolean saveCodeList(CodeListModel codeList) {
		try{
			CodeList codeListEntity=(CodeList) ModelEntityMapper.converModelToEntity(codeList, CodeList.class);
			codeListRepo.saveAndFlush(codeListEntity);
			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public List<CodeListModel> getCodeList(String codeGroup){
		List<CodeListModel> modelList=new ArrayList<CodeListModel>();
		
		try{
			if(!StringUtils.isEmpty(codeGroup)){
				List<CodeList> list=codeListRepo.findByClGroup(codeGroup);

				for(CodeList codeListEntity: list){
					CodeListModel modelObject=(CodeListModel) ModelEntityMapper
							.converEntityToModel(codeListEntity, CodeListModel.class);
					modelList.add(modelObject);
				}
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return modelList;

	}
	
	@Override
	@Transactional
	public Boolean deleteCodeList(int codeId){
		codeListRepo.delete(codeId);
		return true;
	}
	
	@Override
	public CodeListModel getCodeId(int codeId){
		return (CodeListModel) ModelEntityMapper.converEntityToModel(codeListRepo.findOne(codeId), CodeListModel.class);
	}
	
	@Override
	@Transactional
	public List<ChartModel> getDashBoardChart(SearchModel search){
		
		
		
		List<ChartModel> chartData=new ArrayList<ChartModel>(); 
		try{
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			
			Date from_date = formatter.parse(search.getFromDate());
			Date to_date = formatter.parse(search.getToDate());
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String fromdate=dateFormat.format(from_date);
			String todate=dateFormat.format(to_date);
			
			Session session =(Session) em.getDelegate();
			String hsql=" SELECT COUNT(*) AS fieldValue, "
					+ " (select cl.cl_alternate from code_list cl where cl.cl_group='COUNTRY' and cl.code_id=country) AS fieldName "
					+ "  FROM enroll WHERE createDate between '"+fromdate+"' and '"+todate+"'  GROUP BY country  " ;
			chartData=session.createSQLQuery(hsql).setResultTransformer(Transformers.aliasToBean(ChartModel.class)).list();
			
			List<ArrayList<String>> valuesList = new ArrayList<ArrayList<String>>();
			for(int i = 0;i<chartData.size();i++)
			{
				
				ArrayList<String> list= new ArrayList<String>();
				list.add(chartData.get(i).getFieldName());
				list.add(""+chartData.get(i).getFieldValue());
				
				valuesList.add(list);
				
			}
			chartData.get(0).setValues(valuesList);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return chartData;
	
		
		
	}
	
	
	@Override
	@Transactional
	public List<ChartModel> getReportData(SearchModel search){

		List<ChartModel> chartData=new ArrayList<ChartModel>(); 
		try{
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			
			Date from_date = formatter.parse(search.getFromDate());
			Date to_date = formatter.parse(search.getToDate());
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String fromdate=dateFormat.format(from_date);
			String todate=dateFormat.format(to_date);
			
			Session session =(Session) em.getDelegate();
			String hsql=" SELECT COUNT(*) AS fieldValue, "
					+ " (select cl.cl_alternate from code_list cl where cl.cl_group='COUNTRY' and cl.code_id=country) AS fieldName "
					+ "  FROM enroll WHERE createDate between '"+fromdate+"' and '"+todate+"'  GROUP BY country  " ;
			chartData=session.createSQLQuery(hsql).setResultTransformer(Transformers.aliasToBean(ChartModel.class)).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return chartData;
	
		
		
	
	}
	
	
	public String getTemplate(String templateName,List<ChartModel> list){
		try{
			Template template = velocityEngine.getTemplate("config/"+templateName);

			VelocityContext velocityContext = new VelocityContext();
			
			velocityContext.put("body", list);
			

			StringWriter stringWriter = new StringWriter();

			template.merge(velocityContext, stringWriter);
			
			return stringWriter.toString();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String getReport(String templateName,EnrollModel enroll){
		try{
			Template template = velocityEngine.getTemplate("config/"+templateName);

			VelocityContext velocityContext = new VelocityContext();
			
			velocityContext.put("name", enroll.getName());
			velocityContext.put("org", enroll.getOrgName());
			velocityContext.put("country", enroll.getCountryName());
			

			StringWriter stringWriter = new StringWriter();

			template.merge(velocityContext, stringWriter);
			
			return stringWriter.toString();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
