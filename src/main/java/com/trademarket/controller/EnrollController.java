package com.trademarket.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trademarket.model.CodeListModel;
import com.trademarket.model.EnrollModel;
import com.trademarket.service.EnrollService;
import com.trademarket.service.ReportRenderer;
import com.trademarket.utils.CodeListGroup;



@Controller
@RequestMapping("/enroll")
public class EnrollController {
	
	
	@Autowired
	EnrollService enrollService;
	
	@Autowired
	ReportRenderer report;
	
	@RequestMapping("layout")
    public String getLoginPage() {
        return "partials/enroll";
    }
	
	@RequestMapping("codelistlayout")
    public String getCodelistPage() {
        return "partials/codelist";
    }
	
	@RequestMapping("create")
    public String getCreatePage() {
        return "partials/create";
    }
	
	
	
	@RequestMapping("/codeList")
	public @ResponseBody List<CodeListModel> getCodeList(){
		List<CodeListModel> codeList=new ArrayList<CodeListModel>();
		codeList= enrollService.getCodeList(CodeListGroup.COUNTRY.toString());
		return codeList;
	}
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String  saveEnroll(@RequestBody EnrollModel enrollModel) {
		enrollModel.setCreateDate(new Date());
		Long id=enrollService.saveEnroll(enrollModel);	
		
		String retObject="serverPrint";
		
		try{
			if(ReportRenderer.reportType.equalsIgnoreCase("server")){
				HashMap reportArgs = new HashMap();
				
				reportArgs.put("ct_id", id);
				report.WindowsPrinter("barcode.jasper", reportArgs);
			}
			if(ReportRenderer.reportType.equalsIgnoreCase("client")){
				retObject=enrollService.getReport("print.vm", enrollModel);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retObject;
		
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public void  saveCodeList(@RequestBody CodeListModel codeListModel) {
		
		codeListModel.setClGroup(CodeListGroup.COUNTRY.toString());
		codeListModel.setClLabel(codeListModel.getClLabel().toLowerCase());
		codeListModel.setClOrder(1);
		codeListModel.setClAlternate(codeListModel.getClLabel().toUpperCase());
		codeListModel.setClFlag(1);
		
		enrollService.saveCodeList(codeListModel);
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	public @ResponseBody Boolean delete(@RequestBody int Id){
		return enrollService.deleteCodeList(Id);
	}
	
	@RequestMapping(value = "/codeid" , method = RequestMethod.POST)
	public @ResponseBody CodeListModel getcodeId(@RequestBody int Id){
		return enrollService.getCodeId(Id);
	}
	
}
