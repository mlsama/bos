package com.itheima.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerDao;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	//注入customerDao
	@Resource
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> findNoAssociateCustomers() {
		return customerDao.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findHasAssoicateCustomers(Long fixedAreaId) {
		return customerDao.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void associateCustomerToFixedArea(Long fixedAraeId, String customerId) {
		//清空该定区的所有客户
		customerDao.clearCustomer(fixedAraeId);
		
		//给客户绑定到定区
		if(customerId!=null && !customerId.equals("")){
			String[] cIdArray = customerId.split(",");
			for (String cId : cIdArray) {
				customerDao.associateCustomer(fixedAraeId,Long.parseLong(cId));
			}
		}
	}

	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	@Override
	public Customer findByTelephone(String telephone) {
		return customerDao.findByTelephone(telephone);
	}

	@Override
	public void activeCustomer(String telephone) {
		customerDao.activeCustomer(telephone);
	}

	@Override
	public Customer login(String telephone, String password) {
		return customerDao.findByTelephoneAndPassword(telephone,password);
	}

	@Override
	public Customer findByAddress(String address) {
		return customerDao.findByAddress(address);
	}

	@Override
	public Customer findById(Long id) {
		return customerDao.findById(id);
	}

}
