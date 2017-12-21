package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.dao.BaseDao;
import com.itheima.crm.domain.Customer;

public interface CustomerDao extends BaseDao<Customer>{

	//where fixedAreaId is null
	public List<Customer> findByFixedAreaIdIsNull();

	//where fixedAreaId = ?
	public List<Customer> findByFixedAreaId(Long fixedAreaId);

	@Query("update Customer set fixedAreaId=null where fixedAreaId = ?")
	@Modifying
	public void clearCustomer(Long fixedAraeId);

	@Query("update Customer set fixedAreaId=? where id = ?")
	@Modifying
	public void associateCustomer(Long fixedAraeId, long customerId);

	public Customer findByTelephone(String telephone);

	@Query("update Customer set type='1' where telephone = ?")
	@Modifying
	public void activeCustomer(String telephone);

	public Customer findByTelephoneAndPassword(String telephone, String password);

	public Customer findByAddress(String address);

	public Customer findById(Long id);

}
