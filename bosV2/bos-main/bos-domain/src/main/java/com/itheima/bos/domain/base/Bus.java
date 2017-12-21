package com.itheima.bos.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Bus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BUS")
public class Bus implements java.io.Serializable {

	// Fields

	private Long id;
	private Way way; //线路
	private String busNum; //车牌
	private String provider; //承运商
	private String busType; //车型
	private String driver; //司机
	private Long telephone; //电话
	private Long ton; //吨位
	private String remark; //备注

	// Constructors

	/** default constructor */
	public Bus() {
	}

	/** full constructor */
	public Bus(Way way, String busNum, String provider, String busType,
			String driver, Long telephone, Long ton, String remark) {
		this.way = way;
		this.busNum = busNum;
		this.provider = provider;
		this.busType = busType;
		this.driver = driver;
		this.telephone = telephone;
		this.ton = ton;
		this.remark = remark;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_BUS_SEQ",allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE")
	public Way getWay() {
		return this.way;
	}

	public void setWay(Way way) {
		this.way = way;
	}

	@Column(name = "BUS_NUM", length = 30)
	public String getBusNum() {
		return this.busNum;
	}

	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}

	@Column(name = "PROVIDER", length = 30)
	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Column(name = "BUS_TYPE", length = 30)
	public String getBusType() {
		return this.busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	@Column(name = "DRIVER", length = 30)
	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Column(name = "TELEPHONE", precision = 15, scale = 0)
	public Long getTelephone() {
		return this.telephone;
	}

	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	@Column(name = "TON", precision = 10, scale = 0)
	public Long getTon() {
		return this.ton;
	}

	public void setTon(Long ton) {
		this.ton = ton;
	}

	@Column(name = "REMARK", length = 30)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}