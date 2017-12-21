package com.itheima.bos.domain.take_delivery;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.itheima.bos.domain.base.Courier;

/**
 *工单
 */
@Entity
@Table(name = "T_WORK_BILL")
public class WorkBill implements java.io.Serializable {

	// Fields

	private Long id;
	private Order order; //关联订单
	private String pickstate; //取件状态 （ 1: 待取件  2:已取件  3：已取消  ）
	private Date buildtime; //工单创建时间
	private String remark; //备注
	private Courier courierId; //关联快递员

	// Constructors

	/** default constructor */
	public WorkBill() {
	}

	/** full constructor */
	public WorkBill(Order order, String pickstate, Date buildtime,
			String remark, Courier courierId) {
		this.order = order;
		this.pickstate = pickstate;
		this.buildtime = buildtime;
		this.remark = remark;
		this.courierId = courierId;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_WORK_BILL_SEQ",allocationSize=1)
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

	@Column(name = "PICKSTATE", length = 20)
	public String getPickstate() {
		return this.pickstate;
	}

	public void setPickstate(String pickstate) {
		this.pickstate = pickstate;
	}

	@Column(name = "BUILDTIME")
	public Date getBuildtime() {
		return this.buildtime;
	}

	public void setBuildtime(Date buildtime) {
		this.buildtime = buildtime;
	}

	@Column(name = "REMARK", length = 510)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne
	@JoinColumn(name="COURIER_ID")
	public Courier getCourierId() {
		return this.courierId;
	}

	public void setCourierId(Courier courierId) {
		this.courierId = courierId;
	}

}