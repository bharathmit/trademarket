package com.trademarket.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="code_list")
public class CodeList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="code_id", unique=true, nullable=false)
	private int codeId;
	
	@Column(name="cl_group",nullable=false)
	private String clGroup;
	
	@Column(name="cl_label",nullable=false)
	private String clLabel;
	
	@Column(name="cl_order",nullable=false)
	private int clOrder;
	
	@Column(name="cl_alternate",nullable=false)
	private String clAlternate;
	
	@Column(name="cl_flag",nullable=false)
	private int clFlag;
	
	@Column(name="time_stamp", nullable=true,columnDefinition="timestamp default current_timestamp on update current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;

	

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

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	

}
