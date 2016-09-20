package com.trademarket.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChartModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private int fieldValue;
	private String fieldName;
	private String createDate;
	
	private List<ArrayList<String>> values; 
	
	public int getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = Integer.parseInt(fieldValue.toString());
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(Object fieldName) {
		this.fieldName = fieldName.toString();
	}
	public List<ArrayList<String>> getValues() {
		return values;
	}
	public void setValues(List<ArrayList<String>> values) {
		this.values = values;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Object createDate) {
		this.createDate = createDate.toString();
	}
	
	
	
	
	
	
	

}
