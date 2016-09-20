package com.trademarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import com.trademarket.model.ChartModel;
import com.trademarket.model.SearchModel;
import com.trademarket.service.EnrollService;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
	
	@Autowired
	EnrollService enrollService;
	
	@RequestMapping("report/layout")
    public String getReportPage() {
        return "partials/report";
    }
	
	@RequestMapping("/layout")
    public String getDashBoardPage() {
        return "partials/dashboard";
    }
	
	
	
	@RequestMapping(value = "/chart", method = RequestMethod.POST)
	public @ResponseBody List<ChartModel> getChartData(@RequestBody SearchModel searchModel) {
		return enrollService.getDashBoardChart(searchModel);
	}
	
	
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public @ResponseBody String getReportData(@RequestBody SearchModel searchModel) {
		List<ChartModel> list=enrollService.getReportData(searchModel);
		return enrollService.getTemplate("countrywisereport.vm", list);
	}
	
}
