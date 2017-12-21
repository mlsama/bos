package com.itheima.bos.domain.take_delivery;

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
 * WayBill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WAY_BILL")
public class WayBill implements java.io.Serializable {

	// Fields

	private Long id;
	private Order order;
	private String wayBillNum;
	private String sendName;
	private String sendMobile;
	private String sendCompany;
	private Long sendArea;
	private String sendAddress;
	private String arriveCity;
	private String recName;
	private String recMobile;
	private String recCompany;
	private Long recArea;
	private String recAddress;
	private String sendProNum;
	private String goodsType;
	private String payTypeNum;
	private String weight;
	private String actlweit;
	private String num;
	private String feeitemnum;
	private String vol;
	private String floadreqr;
	private String signStatus;
	private String remark;
	//添加字段运单是否作废
	private String isDelete;
	// Constructors
	@Column(name = "ISDELETE", length = 510)
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	/** default constructor */
	public WayBill() {
	}

	/** full constructor */
	public WayBill(Order order, String wayBillNum, String sendName,
			String sendMobile, String sendCompany, Long sendArea,
			String sendAddress, String arriveCity, String recName,
			String recMobile, String recCompany, Long recArea,
			String recAddress, String sendProNum, String goodsType,
			String payTypeNum, String weight, String actlweit, String num,
			String feeitemnum, String vol, String floadreqr, String signStatus,
			String remark) {
		this.order = order;
		this.wayBillNum = wayBillNum;
		this.sendName = sendName;
		this.sendMobile = sendMobile;
		this.sendCompany = sendCompany;
		this.sendArea = sendArea;
		this.sendAddress = sendAddress;
		this.arriveCity = arriveCity;
		this.recName = recName;
		this.recMobile = recMobile;
		this.recCompany = recCompany;
		this.recArea = recArea;
		this.recAddress = recAddress;
		this.sendProNum = sendProNum;
		this.goodsType = goodsType;
		this.payTypeNum = payTypeNum;
		this.weight = weight;
		this.actlweit = actlweit;
		this.num = num;
		this.feeitemnum = feeitemnum;
		this.vol = vol;
		this.floadreqr = floadreqr;
		this.signStatus = signStatus;
		this.remark = remark;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_WAY_BILL_SEQ",allocationSize=1)
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
	@JoinColumn(name = "ORDER_ID")
	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Column(name = "WAY_BILL_NUM", length = 510)
	public String getWayBillNum() {
		return this.wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	@Column(name = "SEND_NAME", length = 200)
	public String getSendName() {
		return this.sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	@Column(name = "SEND_MOBILE", length = 200)
	public String getSendMobile() {
		return this.sendMobile;
	}

	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile;
	}

	@Column(name = "SEND_COMPANY", length = 510)
	public String getSendCompany() {
		return this.sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}

	@Column(name = "SEND_AREA", precision = 10, scale = 0)
	public Long getSendArea() {
		return this.sendArea;
	}

	public void setSendArea(Long sendArea) {
		this.sendArea = sendArea;
	}

	@Column(name = "SEND_ADDRESS", length = 510)
	public String getSendAddress() {
		return this.sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	@Column(name = "ARRIVE_CITY", length = 510)
	public String getArriveCity() {
		return this.arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	@Column(name = "REC_NAME", length = 200)
	public String getRecName() {
		return this.recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	@Column(name = "REC_MOBILE", length = 200)
	public String getRecMobile() {
		return this.recMobile;
	}

	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}

	@Column(name = "REC_COMPANY", length = 510)
	public String getRecCompany() {
		return this.recCompany;
	}

	public void setRecCompany(String recCompany) {
		this.recCompany = recCompany;
	}

	@Column(name = "REC_AREA", precision = 10, scale = 0)
	public Long getRecArea() {
		return this.recArea;
	}

	public void setRecArea(Long recArea) {
		this.recArea = recArea;
	}

	@Column(name = "REC_ADDRESS", length = 510)
	public String getRecAddress() {
		return this.recAddress;
	}

	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}

	@Column(name = "SEND_PRO_NUM", length = 20)
	public String getSendProNum() {
		return this.sendProNum;
	}

	public void setSendProNum(String sendProNum) {
		this.sendProNum = sendProNum;
	}

	@Column(name = "GOODS_TYPE", length = 20)
	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "PAY_TYPE_NUM", length = 20)
	public String getPayTypeNum() {
		return this.payTypeNum;
	}

	public void setPayTypeNum(String payTypeNum) {
		this.payTypeNum = payTypeNum;
	}

	@Column(name = "WEIGHT", length = 20)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Column(name = "ACTLWEIT", length = 20)
	public String getActlweit() {
		return this.actlweit;
	}

	public void setActlweit(String actlweit) {
		this.actlweit = actlweit;
	}

	@Column(name = "NUM", length = 20)
	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Column(name = "FEEITEMNUM", length = 20)
	public String getFeeitemnum() {
		return this.feeitemnum;
	}

	public void setFeeitemnum(String feeitemnum) {
		this.feeitemnum = feeitemnum;
	}

	@Column(name = "VOL", length = 20)
	public String getVol() {
		return this.vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	@Column(name = "FLOADREQR", length = 20)
	public String getFloadreqr() {
		return this.floadreqr;
	}

	public void setFloadreqr(String floadreqr) {
		this.floadreqr = floadreqr;
	}

	@Column(name = "SIGN_STATUS", length = 20)
	public String getSignStatus() {
		return this.signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	@Column(name = "REMARK", length = 510)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}