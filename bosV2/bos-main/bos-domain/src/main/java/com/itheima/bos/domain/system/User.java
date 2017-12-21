package com.itheima.bos.domain.system;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *用户
 */
@Entity
@Table(name = "T_USER")
public class User implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String gender;
	private String telephone;
	private String station;
	private String remark;
	private Set<Role> roles = new HashSet<Role>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String password, String nickname,
			String gender, String telephone, String station, String remark,
			Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.telephone = telephone;
		this.station = station;
		this.remark = remark;
		this.roles = roles;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_USER_SEQ",allocationSize=1)
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

	@Column(name = "NICKNAME", length = 200)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "GENDER", length = 20)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "TELEPHONE", length = 200)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "STATION", length = 2)
	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	@Column(name = "REMARK", length = 510)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}