package com.itheima.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Customer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CUSTOMER")
@XmlRootElement(name="customer")
public class Customer implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String password;
	private String type;
	private String sex;
	private String telephone;
	private String address;
	private String email;
	private Long fixedAreaId; //定区ID

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** full constructor */
	public Customer(String username, String password, String type, String sex,
			String telephone, String address, String email, Long fixedAreaId) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.sex = sex;
		this.telephone = telephone;
		this.address = address;
		this.email = email;
		this.fixedAreaId = fixedAreaId;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_CUSTOMER_SEQ",allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USERNAME", length = 200)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", length = 200)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "SEX", length = 20)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "TELEPHONE", length = 200)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "ADDRESS", length = 510)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FIXED_AREA_ID", precision = 10, scale = 0)
	public Long getFixedAreaId() {
		return this.fixedAreaId;
	}

	public void setFixedAreaId(Long fixedAreaId) {
		this.fixedAreaId = fixedAreaId;
	}

}