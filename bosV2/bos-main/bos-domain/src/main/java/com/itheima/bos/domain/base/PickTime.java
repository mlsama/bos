package com.itheima.bos.domain.base;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * PickTime entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_PICK_TIME")

public class PickTime  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;
     private String usualOn;
     private String usualDown;
     private String saturdayOn;
     private String saturdayDown;
     private String weekOn;
     private String weekDown;
     //添加快递员上班时间
     @JSONField(serialize=false)
     private Set<Courier> couriers = new HashSet<Courier>(0);


    // Constructors

    /** default constructor */
    public PickTime() {
    }

    
    /** full constructor */
    public PickTime(String name, String usualOn, String usualDown, String saturdayOn, String saturdayDown, String weekOn, String weekDown, Set<Courier> couriers) {
        this.name = name;
        this.usualOn = usualOn;
        this.usualDown = usualDown;
        this.saturdayOn = saturdayOn;
        this.saturdayDown = saturdayDown;
        this.weekOn = weekOn;
        this.weekDown = weekDown;
        this.couriers = couriers;
    }

   
    // Property accessors
    @SequenceGenerator(name="generator",sequenceName="T_PICK_TIME_SEQ",allocationSize=1)
    @Id 
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    
    @Column(name="ID", unique=true, nullable=false, precision=10, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="NAME", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    @Column(name="USUAL_ON", length=30)

    public String getUsualOn() {
        return this.usualOn;
    }
    
    public void setUsualOn(String usualOn) {
        this.usualOn = usualOn;
    }
    @Column(name="USUAL_DOWN", length=30)

    public String getUsualDown() {
        return this.usualDown;
    }
    
    public void setUsualDown(String usualDown) {
        this.usualDown = usualDown;
    }
    @Column(name="SATURDAY_ON", length=30)

    public String getSaturdayOn() {
        return this.saturdayOn;
    }
    
    public void setSaturdayOn(String saturdayOn) {
        this.saturdayOn = saturdayOn;
    }
    @Column(name="SATURDAY_DOWN", length=30)

    public String getSaturdayDown() {
        return this.saturdayDown;
    }
    
    public void setSaturdayDown(String saturdayDown) {
        this.saturdayDown = saturdayDown;
    }
    @Column(name="WEEK_ON", length=30)

    public String getWeekOn() {
        return this.weekOn;
    }
    
    public void setWeekOn(String weekOn) {
        this.weekOn = weekOn;
    }
    @Column(name="WEEK_DOWN", length=30)

    public String getWeekDown() {
        return this.weekDown;
    }
    
    public void setWeekDown(String weekDown) {
        this.weekDown = weekDown;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pickTime")

    public Set<Courier> getCouriers() {
        return this.couriers;
    }
    
    public void setCouriers(Set<Courier> couriers) {
        this.couriers = couriers;
    }
   








}