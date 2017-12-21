package com.itheima.bos.dao;

import com.itheima.bos.domain.base.Area;

/**
 * 区域的dao
 * @author lenovo
 *
 */
public interface AreaDao extends BaseDao<Area>{

	/**
	 * 根据省市区名称查询一个区域
	 * @param province
	 * @param city
	 * @param district
	 * @return
	 */
	public Area findByProvinceAndCityAndDistrict(String province, String city, String district);

}
