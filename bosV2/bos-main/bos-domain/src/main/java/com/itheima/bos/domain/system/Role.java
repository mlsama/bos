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
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ROLE")
public class Role implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String keyword;
	private String description;
	private Set<Resources> resourceses = new HashSet<Resources>(0);
	@JSONField(serialize=false)
	private Set<User> users = new HashSet<User>(0);
	
	//添加一个临时checked属性
	private Boolean checked;
	
	// Constructors
	@Transient
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/** default constructor */
	public Role() {
	}

	/** full constructor */
	public Role(String name, String keyword, String description,
			Set<Resources> resourceses, Set<User> users) {
		this.name = name;
		this.keyword = keyword;
		this.description = description;
		this.resourceses = resourceses;
		this.users = users;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_ROLE_SEQ",allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "KEYWORD", length = 200)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "DESCRIPTION", length = 510)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(name = "T_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID", updatable = false) })
	public Set<Resources> getResourceses() {
		return this.resourceses;
	}

	public void setResourceses(Set<Resources> resourceses) {
		this.resourceses = resourceses;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}