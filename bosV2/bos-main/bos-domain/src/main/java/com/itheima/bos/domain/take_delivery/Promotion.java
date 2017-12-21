package com.itheima.bos.domain.take_delivery;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 宣传任务（促销活动）
 */
@Entity
@Table(name = "T_PROMOTION")
@XmlRootElement(name="promotion")
public class Promotion implements java.io.Serializable {

	// Fields

	private Long id;
	private String title; //标题
	private String titleImg;//图片地址
	private String activeScope; //活动范围
	private Date startDate; //活动开始时间
	private Date endDate; //活动结束时间
	private String status; // 0：过期   1：有效
	private String description; //文章内容

	// Constructors

	/** default constructor */
	public Promotion() {
	}

	/** full constructor */
	public Promotion(String title, String titleImg, String activeScope,
			Date startDate, Date endDate, String status, String description) {
		this.title = title;
		this.titleImg = titleImg;
		this.activeScope = activeScope;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_PROMOTION_SEQ",allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TITLE", length = 510)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "TITLE_IMG", length = 510)
	public String getTitleImg() {
		return this.titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	@Column(name = "ACTIVE_SCOPE", length = 510)
	public String getActiveScope() {
		return this.activeScope;
	}

	public void setActiveScope(String activeScope) {
		this.activeScope = activeScope;
	}

	@Column(name = "START_DATE")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Lob
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}