package com.itheima.bos.service.impl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.AreaDao;
import com.itheima.bos.dao.FixedAreaDao;
import com.itheima.bos.dao.OrderDao;
import com.itheima.bos.dao.WorkBillDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.CourierService;
import com.itheima.bos.service.OrderService;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.utils.MySmsUtils;
import com.itheima.crm.domain.Customer;

@Service
@Transactional // 业务层执行事务操作
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
	
	private OrderDao orderDao;
	
	@Resource
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(orderDao);
	}
	
	//注入areaDao
	@Resource
	private AreaDao areaDao;
	//注入fixedAreaDao
	@Resource
	private FixedAreaDao fixedAreaDao;
	
	//注入workBillDao
	@Resource
	private WorkBillDao workBillDao;

	@Override
	public void saveOrder(Order order) {
		//System.out.println("订单数据："+order);
		
		//生成订单号
		order.setOrderNum(UUID.randomUUID().toString());
		
		//处理寄件人区域对象和收件人区域对象（获取持久态的区域对象）
		if( order.getSendArea()!=null ){
			Area persistSendArea = areaDao.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(),order.getSendArea().getCity(),order.getSendArea().getDistrict());
			//订单关联寄件人区域
			order.setSendArea(persistSendArea);
		}
		
		if( order.getRecArea()!=null ){
			Area persistRecArea = areaDao.findByProvinceAndCityAndDistrict(order.getRecArea().getProvince(),order.getRecArea().getCity(),order.getRecArea().getDistrict());
			//订单关联收件人区域
			order.setRecArea(persistRecArea);
		}
		
		//订单状态
		order.setStatus("1");//1:已下单
		//下单时间
		order.setOrderTime(new Date());
		
		//实现自动分单的逻辑 (给订单指派快递员去取件)
		/**
		 * 1)根据下单的寄件人详细地址完全匹配客户地址进行自动分单
		 *      下单的寄件人详细地址  -> 查询CRM系统的客户表（地址）-> 查询到定区  -> 查询到快递员（发送短信）
		 * 2)根据下单的寄件人区域和详细地址模糊匹配进行自动分单
		 *      下单的寄件人区域（省市区） -> 查询区域表 -> 查询该区域的所有分区 -> 根据下单的寄件人详细地址模糊匹配分区关键词和辅助关键词 ->
		 *      			 查询定区  -> 查询到快递员（发送短信）
		 */
		// 1)根据下单的寄件人详细地址完全匹配客户地址进行自动分单
		//根据详细地址查询客户表
		Customer customer = WebClient
			.create(Constant.CRM_URL+"/services/customerService/findByAddress?address="+order.getSendAddress())
			.accept(MediaType.APPLICATION_JSON)
			.get(Customer.class);
		
		if(customer!=null){
			Long fixedAreaId = customer.getFixedAreaId();
			if(fixedAreaId!=null){
				//该客户已经绑定了一个定区
				//根据定区ID查询定区对象
				FixedArea persitFixedArea = fixedAreaDao.findOne(fixedAreaId);
				if(persitFixedArea!=null){
					//查询快递员
					Courier courier = persitFixedArea.getCourier();
					if(courier!=null){
						//自动分单成功
						System.out.println("根据下单的寄件人详细地址完全匹配客户地址进行自动分单成功啦...");
						
						//订单类型
						order.setOrderType("1");//自动分单
						order.setStatus("2");
						//先保存订单，让订单成为持久态
						orderDao.save(order);
						
						//再创建工单
						createWorkBill(order,courier);
						
						return;
					}
				}
			}
		}
		
		//2)根据下单的寄件人区域和详细地址模糊匹配进行自动分单
		//根据寄件人的省市区匹配区域数据
		Area persistSendArea = areaDao.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(),order.getSendArea().getCity(),order.getSendArea().getDistrict());
		if(persistSendArea!=null){
			//获取该区域下的所有分区
			Set<SubArea> subAreas = persistSendArea.getSubAreas();
			//遍历分区
			for (SubArea subArea : subAreas) {
				//根据详细地址 模糊匹配 分区的关键词和赋值关键词
				if( order.getSendAddress().contains(subArea.getKeyWords()) 
						|| order.getSendAddress().contains(subArea.getAssistKeyWords())  ){
					//查询定区
					FixedArea fixedArea = subArea.getFixedArea();
					if(fixedArea!=null){
						//查询快递员
						Courier courier = fixedArea.getCourier();
						if(courier!=null){
							//自动分单
							System.out.println("根据下单的寄件人区域和详细地址模糊匹配进行自动分单成功啦...");
							
							//订单类型
							order.setOrderType("1");//自动分单
							order.setStatus("2");
							//先保存订单，让订单成为持久态
							orderDao.save(order);
							//再创建工单
							createWorkBill(order,courier);
							return;
						}
					}
				}
			}
		}
		
		//人工分单
		order.setOrderType("2");//人工分单
		//保存订单
		orderDao.save(order);
	}
	
	//创建工单，发送短信
	public void createWorkBill(Order order,Courier courier){
		//创建工单
		WorkBill wb = new WorkBill();
		//关联订单
		wb.setOrder(order);
		//设置取件状态
		wb.setPickstate("1");//1:待取件
		//设置创建时间
		wb.setBuildtime(new Date());
		//关联快递员
		wb.setCourierId(courier);
		//备注
		wb.setRemark("该工单由"+courier.getName()+"来取件完成");
		//保存工单
		workBillDao.save(wb);
		
		//发送取件短信给快递员
		/*SmsUtils.sendSms(
				"物流系统", 
				"{\"name\":\""+courier.getName()+"\",\"address\":\""+order.getSendAddress()+"\",\"phone\":\""+order.getSendMobile()+"\"}", 
				courier.getTelephone(),
				"SMS_62405562");*/
		//发送取件短信给快递员,使用自己的工具类
		MySmsUtils.sendSms("物流系统", 
				"{\"name\":\""+courier.getName()+"\",\"address\":\""+order.getSendAddress()+"\",\"phone\":\""+order.getSendMobile()+"\",\"message\":\""+order.getSendMobileMsg()+"\"}", 
				courier.getTelephone(), 
				"SMS_85565036");
	}

	@Override
	public Order findByOrderNum(String orderNum) {
		return orderDao.findByOrderNum(orderNum);
	}

	@Override
	public void cancelOrder(String status, String orderNum) {
		orderDao.cancelOrder(orderNum);
	}
	//注入查询快递员
	@Resource
	private CourierService courierService;
	
	@Override
	public void bindOrderToCourier(Long orderId, Long courierId) {
		Order persistOrder = orderDao.findOne(orderId);
		Courier courier = courierService.findOne(courierId);
		//创建工单
		createWorkBill(persistOrder, courier);
		//修改订单状态为已分单
		persistOrder.setStatus("2");
	}
	
}
