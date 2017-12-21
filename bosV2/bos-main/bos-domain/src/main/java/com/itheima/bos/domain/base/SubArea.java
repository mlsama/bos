package com.itheima.bos.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 分区
 */
@Entity
@Table(name="T_SUB_AREA")

public class SubArea  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Area area; //所属区域
     private FixedArea fixedArea;//所属定区
     private String startNum; //开始门牌号
     private String endNum;//结束门牌号
     private String keyWords; //分区关键词（自动分单）
     private String assistKeyWords; //辅助关键词（自动分单）


    // Constructors

    /** default constructor */
    public SubArea() {
    }

    
    /** full constructor */
    public SubArea(Area area, FixedArea fixedArea, String startNum, String endNum, String keyWords, String assistKeyWords) {
        this.area = area;
        this.fixedArea = fixedArea;
        this.startNum = startNum;
        this.endNum = endNum;
        this.keyWords = keyWords;
        this.assistKeyWords = assistKeyWords;
    }

   
    // Property accessors
    @SequenceGenerator(name="generator",sequenceName="T_SUB_AREA_SEQ",allocationSize=1)
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
        @JoinColumn(name="AREA_ID")

    public Area getArea() {
        return this.area;
    }
    
    public void setArea(Area area) {
        this.area = area;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="FIXEDAREA")

    public FixedArea getFixedArea() {
        return this.fixedArea;
    }
    
    public void setFixedArea(FixedArea fixedArea) {
        this.fixedArea = fixedArea;
    }
    
    @Column(name="START_NUM", length=200)

    public String getStartNum() {
        return this.startNum;
    }
    
    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }
    
    @Column(name="END_NUM", length=200)

    public String getEndNum() {
        return this.endNum;
    }
    
    public void setEndNum(String endNum) {
        this.endNum = endNum;
    }
    
    @Column(name="KEY_WORDS", length=510)

    public String getKeyWords() {
        return this.keyWords;
    }
    
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
    
    @Column(name="ASSIST_KEY_WORDS", length=510)

    public String getAssistKeyWords() {
        return this.assistKeyWords;
    }
    
    public void setAssistKeyWords(String assistKeyWords) {
        this.assistKeyWords = assistKeyWords;
    }
   








}