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
 * 收派标准实体
 */
@Entity
@Table(name="T_STANDARD")

public class Standard  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;
     private Long minWeight;
     private Long maxWeight;
     private Long minLength;
     private Long maxLength;
     @JSONField(serialize=false) // 取消该属性转换
     private Set<Courier> couriers = new HashSet<Courier>(0);


    // Constructors

    /** default constructor */
    public Standard() {
    }

	/** minimal constructor */
    public Standard(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public Standard(String name, Long minWeight, Long maxWeight, Long minLength, Long maxLength, Set<Courier> couriers) {
        this.name = name;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.couriers = couriers;
    }

   
    // Property accessors
    @SequenceGenerator(name="generator",sequenceName="T_STANDARD_SEQ",allocationSize=1)
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="ID", unique=true, nullable=false, precision=10, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="NAME", nullable=false, length=200)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="MIN_WEIGHT", precision=10, scale=0)

    public Long getMinWeight() {
        return this.minWeight;
    }
    
    public void setMinWeight(Long minWeight) {
        this.minWeight = minWeight;
    }
    
    @Column(name="MAX_WEIGHT", precision=10, scale=0)

    public Long getMaxWeight() {
        return this.maxWeight;
    }
    
    public void setMaxWeight(Long maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    @Column(name="MIN_LENGTH", precision=10, scale=0)

    public Long getMinLength() {
        return this.minLength;
    }
    
    public void setMinLength(Long minLength) {
        this.minLength = minLength;
    }
    
    @Column(name="MAX_LENGTH", precision=10, scale=0)

    public Long getMaxLength() {
        return this.maxLength;
    }
    
    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="standard")

    public Set<Courier> getCouriers() {
        return this.couriers;
    }
    
    public void setCouriers(Set<Courier> couriers) {
        this.couriers = couriers;
    }

	@Override
	public String toString() {
		return "Standard [id=" + id + ", name=" + name + ", minWeight=" + minWeight + ", maxWeight=" + maxWeight
				+ ", minLength=" + minLength + ", maxLength=" + maxLength + "]";
	}
   








}