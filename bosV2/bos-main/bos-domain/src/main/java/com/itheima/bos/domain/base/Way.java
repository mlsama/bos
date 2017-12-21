package com.itheima.bos.domain.base;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Way entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WAY")
public class Way implements java.io.Serializable {

	// Fields
	private Long id;
	private String wayType;
	@JSONField(serialize=false)
	private Set<Bus> buses = new HashSet<Bus>(0);

	// Constructors

	/** default constructor */
	public Way() {
	}

	/** full constructor */
	public Way(String wayType, Set<Bus> buses) {
		this.wayType = wayType;
		this.buses = buses;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="T_WAY_SEQ",allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WAY_TYPE", length = 20)
	public String getWayType() {
		return this.wayType;
	}

	public void setWayType(String wayType) {
		this.wayType = wayType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "way")
	public Set<Bus> getBuses() {
		return this.buses;
	}

	public void setBuses(Set<Bus> buses) {
		this.buses = buses;
	}

}