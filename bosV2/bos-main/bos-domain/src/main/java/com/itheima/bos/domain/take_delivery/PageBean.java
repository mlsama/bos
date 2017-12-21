package com.itheima.bos.domain.take_delivery;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * 自行设计的分页对象，用于封装分页的数据
 * @author lenovo
 *
 */
@XmlRootElement(name="pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

	//每页列表数据
	private List<T> content;
	
	//总记录数
	private Long totalCount;

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
