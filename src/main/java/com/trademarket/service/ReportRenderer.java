package com.trademarket.service;

import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

@Service
public class ReportRenderer {
	
	private static Logger log = Logger.getLogger(ReportRenderer.class);
	
	@Autowired
	ServletContext context;
	
	private static Connection connection;
	
	public static String dbDriver;
	public static String dbUrl;
	public static String dbUser;
	public static String dbPassword;
	public static String reportType;
	
	public Connection getConnection() 
	{
		if (connection == null)
		{
			try 
			{
				Class.forName(dbDriver);
				
				connection = DriverManager.getConnection (dbUrl,dbUser,dbPassword);
				log.info("connection success:");
			} 
			catch(Exception e) 
			{
				log.error("Reports database failed");
			}
		}
		
		return connection;
	}
	
	
	public void WindowsPrinter(String reportName,HashMap reportArgs) throws Exception
	{
		try{
			
			PrinterJob printerJob = PrinterJob.getPrinterJob();
			
			PrintService printObject = PrintServiceLookup.lookupDefaultPrintService();
			
			printerJob.setPrintService(printObject);
			
			InputStream reportStream = context.getResourceAsStream("WEB-INF/reports/" + reportName);
			 
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream,reportArgs, getConnection());
			
			JasperPrintManager.printReport(jasperPrint, false);
			
			log.info("print Success");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
        
	}


	


}
