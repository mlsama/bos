package com.itheima.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 泛型dao
 * @author lenovo
 *
 */
@NoRepositoryBean  // 放弃spring data jpa创建代理对象
public interface BaseDao<T> extends JpaRepository<T, Long>,JpaSpecificationExecutor<T>{

}
