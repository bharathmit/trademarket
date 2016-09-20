package com.trademarket.model;

import java.io.Serializable;

public class CodeListModel implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String clGroup;
	private String clLabel;
	private int codeId;
	private int clOrder;
	private String clAlternate;
	private int clFlag;
	
	public String getClGroup() {
		return clGroup;
	}
	public void setClGroup(String clGroup) {
		this.clGroup = clGroup;
	}
	public String getClLabel() {
		return clLabel;
	}
	public void setClLabel(String clLabel) {
		this.clLabel = clLabel;
	}
	public int getClOrder() {
		return clOrder;
	}
	public void setClOrder(int clOrder) {
		this.clOrder = clOrder;
	}
	public String getClAlternate() {
		return clAlternate;
	}
	public void setClAlternate(String clAlternate) {
		this.clAlternate = clAlternate;
	}
	public int getClFlag() {
		return clFlag;
	}
	public void setClFlag(int clFlag) {
		this.clFlag = clFlag;
	}
	public int getCodeId() {
		return codeId;
	}
	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}
	
}
