package com.trademarket.service;

import java.util.List;

import com.trademarket.model.ChartModel;
import com.trademarket.model.CodeListModel;
import com.trademarket.model.EnrollModel;
import com.trademarket.model.SearchModel;

public interface EnrollService {
	
	public Long saveEnroll(EnrollModel enroll);
	
	public Boolean saveCodeList(CodeListModel codeList);
	public List<CodeListModel> getCodeList(String codeGroup);
	
	public Boolean deleteCodeList(int codeId);
	public CodeListModel getCodeId(int codeId);
	
	public List<ChartModel> getDashBoardChart(SearchModel search);
	
	public List<ChartModel> getReportData(SearchModel search);
	
	public String getTemplate(String templateName,List<ChartModel> list);
	
	public String getReport(String templateName,EnrollModel enroll);

}
