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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Resources entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RESOURCE")
public class Resources implements java.io.Serializable {

	// Fields

	private Long id;
	private String name; //资源名称
	private String grantKey; //授权码
	private String pageUrl; //菜单链接
	private Long seq; //排序字段
	private String resourceType;  //资源类型：  0：菜单 1 ：按钮
	private String icon; //菜单图标
	@JSONField(name="_parentId") // 转出该字段的时候修改名称
	private Long pid; // 父资源ID
	private Boolean open;
	@JSONField(serialize=false)
	private Set<Role> roles = new HashSet<Role>(0);
	
	//添加一个临时属性，用于回显勾选效果
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
	public Resources() {
	}

	/** full constructor */
	public Resources(String name, String grantKey, String pageUrl, Long seq,
			String resourceType, String icon, Long pid, Boolean open,
			Set<Role> roles) {
		this.name = name;
		this.grantKey = grantKey;
		this.pageUrl = pageUrl;
		this.seq = seq;
		this.resourceType = resourceType;
		this.icon = icon;
		this.pid = pid;
		this.open = open;
		this.roles = roles;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_RESOURCE_SEQ",allocationSize=1)
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

	@Column(name = "GRANT_KEY", length = 200)
	public String getGrantKey() {
		return this.grantKey;
	}

	public void setGrantKey(String grantKey) {
		this.grantKey = grantKey;
	}

	@Column(name = "PAGE_URL", length = 510)
	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	@Column(name = "SEQ", precision = 10, scale = 0)
	public Long getSeq() {
		return this.seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "RESOURCE_TYPE", length = 20)
	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "ICON", length = 200)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "PID", precision = 10, scale = 0)
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "OPEN", precision = 1, scale = 0)
	public Boolean getOpen() {
		return this.open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "resourceses")
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}