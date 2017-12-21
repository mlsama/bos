package com.itheima.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Standard;

/**
 * JpaRepository接口：已经拥有了开发中常用的CRUD方法
 * 泛型一：当前需要操作的实体类型
 * 泛型二： 当前操作的实体的主键属性类型
 * @author lenovo
 *
 */
public interface StandardDao extends BaseDao<Standard>{
	//查询收派标准名称"5-10公斤"
	//from Standard where name = ? 
	public List<Standard> findByNameIs(String name);
	
	//模糊查询(驼峰式名称)
	public List<Standard> findByNameLike(String name);
	
	//两个条件： 查询收派标准名称包含"10"，且最小重量为5
	public List<Standard> findByNameLikeAndMinWeight(String name,Long minWeight);
	
	// MinWeight between ? and ?
	public List<Standard> findByMinWeightBetween(Long startWeight,Long endWeight);
	
	//@Query：用于查询与修改操作   参数是JPQL（和HQL类似的）
	@Query("from Standard where name = ?")
	public List<Standard> queryName(String name);
	
	@Query("from Standard where name like ? and minWeight = ?")
	public List<Standard> queryName2(String name,Long minWeight);
	
	//nativeQuery: 是否执行本地查询sql（默认为false）
	@Query(value="select * from t_standard where name like ? and min_weight = ?",nativeQuery=true)
	public List<Standard> queryName3(String name,Long minWeight);
	
	//把id=3的数据maxWeight修改为30
	@Query("update Standard set maxWeight=? where id=?")
	@Modifying // 把方法标记为更新方法
	public void updateMaxWeight(Long maxWeight,Long id);

	//根据name查询一个收派标准
	public Standard findByName(String standardName);
	
}
