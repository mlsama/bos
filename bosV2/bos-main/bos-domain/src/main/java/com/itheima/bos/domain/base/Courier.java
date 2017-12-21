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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * Courier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_COURIER")

public class Courier  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Standard standard;
     private String courierNum;
     private String name;
     private String telephone;
     private String pda;
     private String checkPwd;
     private String company;
     //取消定区转换
     @JSONField(serialize=false)
     private Set<FixedArea> fixedAreas = new HashSet<FixedArea>(0);
    
     private PickTime pickTime;

    // Constructors

    /** default constructor */
    public Courier() {
    }

    
    /** full constructor */
    public Courier(PickTime pickTime,Standard standard, String courierNum, String name, String telephone, String pda, String checkPwd, String company, Set<FixedArea> fixedAreas) {
        this.standard = standard;
        this.courierNum = courierNum;
        this.name = name;
        this.telephone = telephone;
        this.pda = pda;
        this.checkPwd = checkPwd;
        this.company = company;
        this.fixedAreas = fixedAreas;
        this.pickTime = pickTime;
    }

   
    // Property accessors
    @SequenceGenerator(name="generator",sequenceName="T_COURIER_SEQ",allocationSize=1)
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    
    @Column(name="ID", unique=true, nullable=false, precision=10, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="STANDARD")

    public Standard getStandard() {
        return this.standard;
    }
    
    public void setStandard(Standard standard) {
        this.standard = standard;
    }
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="WORK_TIME")

	public PickTime getPickTime() {
	    return this.pickTime;
	}
	
	public void setPickTime(PickTime pickTime) {
	    this.pickTime = pickTime;
}
    
    @Column(name="COURIER_NUM", length=200)

    public String getCourierNum() {
        return this.courierNum;
    }
    
    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }
    
    @Column(name="NAME", length=200)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="TELEPHONE", length=200)

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Column(name="PDA", length=200)

    public String getPda() {
        return this.pda;
    }
    
    public void setPda(String pda) {
        this.pda = pda;
    }
    
    @Column(name="CHECK_PWD", length=200)

    public String getCheckPwd() {
        return this.checkPwd;
    }
    
    public void setCheckPwd(String checkPwd) {
        this.checkPwd = checkPwd;
    }
    
    @Column(name="COMPANY", length=510)

    public String getCompany() {
        return this.company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="courier")

    public Set<FixedArea> getFixedAreas() {
        return this.fixedAreas;
    }
    
    public void setFixedAreas(Set<FixedArea> fixedAreas) {
        this.fixedAreas = fixedAreas;
    }
   


}