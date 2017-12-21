package com.itheima.bos.domain.take_delivery;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.itheima.bos.domain.base.Area;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ORDER")
@XmlRootElement(name="order")
public class Order implements java.io.Serializable {

	// Fields
	private Long id;
	private String orderNum; //订单号,系统生成
	private Long customerId; //客户编号
	
	private String sendName; //寄件人姓名
	private String sendMobile;//寄件人手机
	private Area sendArea; //寄件人区域（省市区）
	private String sendCompany; //寄件人公司
	private String sendAddress; //寄件人详细地址
	
	private String recName; //收件人姓名
	private String recMobile;  //收件人手机
	private Area recArea; //收件人区域（省市区）
	private String recCompany;  //收件人公司
	private String recAddress;  //收件人详细地址
	
	private String sendProNum; //快递产品类型
	private String goodsType; //货物类型
	private String payTypeNum; //付款方式
	private String weight; //重量
	private String remark; //备注
	private String sendMobileMsg; //给小哥捎话
	
	private String orderType; //订单类型 （ 1：自动分单      2：人工分单  ）
	private String status; //订单状态（   1：已下单  2：已分单  3：已发货   4：到达网点  5：派送中  6：已签收 ）
	private Date orderTime;  //下单时间
	
	//工单
	@JSONField(serialize=false)
	private Set<WorkBill> workBills = new HashSet<WorkBill>(0);
	//运单
	@JSONField(serialize=false)
	private Set<WayBill> wayBills = new HashSet<WayBill>(0);

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** full constructor */
	public Order(String orderNum, Long customerId, String sendName,
			String sendMobile, Area sendArea, String sendCompany,
			String sendAddress, String recName, String recMobile, Area recArea,
			String recCompany, String recAddress, String sendProNum,
			String goodsType, String payTypeNum, String weight, String remark,
			String sendMobileMsg, String orderType, String status,
			Date orderTime, Set<WorkBill> workBills, Set<WayBill> wayBills) {
		this.orderNum = orderNum;
		this.customerId = customerId;
		this.sendName = sendName;
		this.sendMobile = sendMobile;
		this.sendArea = sendArea;
		this.sendCompany = sendCompany;
		this.sendAddress = sendAddress;
		this.recName = recName;
		this.recMobile = recMobile;
		this.recArea = recArea;
		this.recCompany = recCompany;
		this.recAddress = recAddress;
		this.sendProNum = sendProNum;
		this.goodsType = goodsType;
		this.payTypeNum = payTypeNum;
		this.weight = weight;
		this.remark = remark;
		this.sendMobileMsg = sendMobileMsg;
		this.orderType = orderType;
		this.status = status;
		this.orderTime = orderTime;
		this.workBills = workBills;
		this.wayBills = wayBills;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_ORDER_SEQ",allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ORDER_NUM", length = 510)
	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "CUSTOMER_ID", precision = 10, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	@ManyToOne
	@JoinColumn(name="SEND_AREA")
	public Area getSendArea() {
		return this.sendArea;
	}

	public void setSendArea(Area sendArea) {
		this.sendArea = sendArea;
	}

	@Column(name = "SEND_COMPANY", length = 510)
	public String getSendCompany() {
		return this.sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}

	@Column(name = "SEND_ADDRESS", length = 510)
	public String getSendAddress() {
		return this.sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
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

	@ManyToOne
	@JoinColumn(name="REC_AREA")
	public Area getRecArea() {
		return this.recArea;
	}

	public void setRecArea(Area recArea) {
		this.recArea = recArea;
	}

	@Column(name = "REC_COMPANY", length = 510)
	public String getRecCompany() {
		return this.recCompany;
	}

	public void setRecCompany(String recCompany) {
		this.recCompany = recCompany;
	}

	@Column(name = "REC_ADDRESS", length = 510)
	public String getRecAddress() {
		return this.recAddress;
	}

	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}

	@Column(name = "SEND_PRO_NUM", length = 200)
	public String getSendProNum() {
		return this.sendProNum;
	}

	public void setSendProNum(String sendProNum) {
		this.sendProNum = sendProNum;
	}

	@Column(name = "GOODS_TYPE", length = 200)
	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "PAY_TYPE_NUM", length = 200)
	public String getPayTypeNum() {
		return this.payTypeNum;
	}

	public void setPayTypeNum(String payTypeNum) {
		this.payTypeNum = payTypeNum;
	}

	@Column(name = "WEIGHT", length = 200)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Column(name = "REMARK", length = 510)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SEND_MOBILE_MSG", length = 510)
	public String getSendMobileMsg() {
		return this.sendMobileMsg;
	}

	public void setSendMobileMsg(String sendMobileMsg) {
		this.sendMobileMsg = sendMobileMsg;
	}

	@Column(name = "ORDER_TYPE", length = 2)
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ORDER_TIME")
	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	public Set<WorkBill> getWorkBills() {
		return this.workBills;
	}

	public void setWorkBills(Set<WorkBill> workBills) {
		this.workBills = workBills;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	public Set<WayBill> getWayBills() {
		return this.wayBills;
	}

	public void setWayBills(Set<WayBill> wayBills) {
		this.wayBills = wayBills;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNum=" + orderNum + ", customerId=" + customerId + ", sendName=" + sendName
				+ ", sendMobile=" + sendMobile + ", sendArea=" + sendArea + ", sendCompany=" + sendCompany
				+ ", sendAddress=" + sendAddress + ", recName=" + recName + ", recMobile=" + recMobile + ", recArea="
				+ recArea + ", recCompany=" + recCompany + ", recAddress=" + recAddress + ", sendProNum=" + sendProNum
				+ ", goodsType=" + goodsType + ", payTypeNum=" + payTypeNum + ", weight=" + weight + ", remark="
				+ remark + ", sendMobileMsg=" + sendMobileMsg + ", orderType=" + orderType + ", status=" + status
				+ ", orderTime=" + orderTime + "]";
	}

}