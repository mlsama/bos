package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.CourierService;
import com.itheima.bos.service.StandardService;
import com.itheima.bos.utils.ExcelConvertUtils;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/courier")
public class CourierAction extends BaseAction<Courier> {
	
	// 注入standardService
	@Resource
	private StandardService standardService;

	private CourierService courierService;
	
	@Resource
	public void setCourierService(CourierService courierService) {
		this.courierService = courierService;
		super.setBaseService(courierService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Courier> buildSpecification() {
		final Courier courier = this.getModel();
		//添加一个Specification条件
		Specification<Courier> spec = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//根据页面传递的参数进行组合条件查询
				//创建Predicate类型集合封装条件查询结果
				List<Predicate> preList = new ArrayList<>();
				//判断工号框是否有输入数据
				if (courier.getCourierNum()!=null&&!"".equals(courier.getCourierNum())) {
					preList.add( cb.equal(root.get("courierNum").as(String.class),courier.getCourierNum()));
				}
				//判断姓名框是否有录入数据
				if (courier.getName()!=null&&!"".equals(courier.getName())) {
					preList.add(cb.like(root.get("name").as(String.class), "%"+courier.getName()+"%"));
				}
				//判断电话号码是否有录入数据
				if (courier.getTelephone()!=null&&!"".equals(courier.getTelephone())) {
					preList.add(cb.like(root.get("telephone").as(String.class),"%"+courier.getTelephone()+"%"));
				}
				
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}
			
			
		};
		return spec;
	}
	
	
	// 接收文件信息
	private File excelFile;

	// 这个setter方法可以接收file的内容
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	/**
	 * excel导入
	 * 
	 * @return
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	@Action("batchImport")
	public String batchImport() throws FileNotFoundException, Exception {
	try { 
		// 1)读取工作簿
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));

		// 2)读取工作单
		HSSFSheet sheet = wb.getSheet("courier");

		// 创建List集合,存放所有courier
		List<Courier> courierList = new ArrayList<Courier>();
		// 遍历行
		for (Row row : sheet) {
			// 跳过第一行
			if (row.getRowNum() == 0) {
				continue;
			}

			// 取出每行的列数据
			String courierNum = ExcelConvertUtils.getExcelValue(row.getCell(0));
			String name = ExcelConvertUtils.getExcelValue(row.getCell(1));
			String telephone = ExcelConvertUtils.getExcelValue(row.getCell(2));
			String pda = ExcelConvertUtils.getExcelValue(row.getCell(3));
			String checkPwd = ExcelConvertUtils.getExcelValue(row.getCell(4));
			String company = ExcelConvertUtils.getExcelValue(row.getCell(5));
			String standardName = ExcelConvertUtils.getExcelValue(row.getCell(6));

			// 创建courier对象,然后封装列数据
			Courier courier = new Courier();

			courier.setCourierNum(courierNum);
			courier.setName(name);
			courier.setTelephone(telephone);
			courier.setPda(pda);
			courier.setCheckPwd(checkPwd);
			courier.setCompany(company);
			// 根据收派标准名字查询Standard
			if(standardName!=null && !standardName.equals("")){
				Standard standard = standardService.findByName(standardName);
				if(standard!=null){
					courier.setStandard(standard);
				}
			}
			
			// 放入List集合
			courierList.add(courier);
		}
			// 批量保存
			courierService.save(courierList);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
	
	
	
	
	/**
	 * 导出相关搜索条件的快递员.xml文件
	 * @throws IOException 
	 */
	@Action("batchExport")
	public String batchExport() throws IOException{
		Specification<Courier> spec = buildSpecification();
		//根据条件对象查询符合条件的集合
		List<Courier> list = courierService.findAll(spec);
		
		
		//生成excel文件
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作单
		HSSFSheet sheet = workbook.createSheet("courier");
		//创建表头
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("快递员工号");
		header.createCell(1).setCellValue("姓名");
		header.createCell(2).setCellValue("电话");
		header.createCell(3).setCellValue("PDA号码");
		header.createCell(4).setCellValue("查台密码");
		header.createCell(5).setCellValue("公司");
		header.createCell(6).setCellValue("收派标准");
		
		//将查询出来的list的courier数据遍历到除表头外的工作行中
		for (int i = 0; i < list.size(); i++) {
			HSSFRow row = sheet.createRow(i+1);
			Courier courier = list.get(i);
			row.createCell(0).setCellValue(courier.getCourierNum());
			row.createCell(1).setCellValue(courier.getName());
			row.createCell(2).setCellValue(courier.getTelephone());
			row.createCell(3).setCellValue(courier.getPda());
			row.createCell(4).setCellValue(courier.getCheckPwd());
			row.createCell(5).setCellValue(courier.getCompany());
			if (courier.getStandard()==null) {
				row.createCell(6).setCellValue("");
			}else{
				row.createCell(6).setCellValue(courier.getStandard().getName());
			}
			
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置响应头
		response.setHeader("content-disposition", "attachment;filename=courier.xls");
		//excel工作簿写出到浏览器
		workbook.write(response.getOutputStream());
		
		return NONE;
	}
	
	/**
	 * 根据订单查询分区下属的所有快递员
	 * @return
	 * @throws Exception 
	 */
	@Action("findCourierByOrder")
	public String findCourierByOrder() throws Exception{
		List<Courier> couriers = courierService.findCourierByOrder(this.getUuid());
		result.put("total", couriers.size());
		result.put("rows", couriers);
		return writeJson(result);
	}
}
