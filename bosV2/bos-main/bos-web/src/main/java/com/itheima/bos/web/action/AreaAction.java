package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
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

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;
import com.itheima.bos.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/area")
public class AreaAction extends BaseAction<Area> {

	private AreaService areaService;
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
		super.setBaseService(areaService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Area> buildSpecification() {
		final Area model = this.getModel();
		// 添加一个Specification条件
		Specification<Area> spec = new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();

				//省
				if(model.getProvince()!=null && !model.getProvince().equals("")){
					preList.add( cb.like(root.get("province").as(String.class), "%"+model.getProvince()+"%")  );
				}
				
				//市
				if(model.getCity()!=null && !model.getCity().equals("")){
					preList.add( cb.like(root.get("city").as(String.class), "%"+model.getCity()+"%")  );
				}
				
				//区
				if(model.getDistrict()!=null && !model.getDistrict().equals("")){
					preList.add( cb.like(root.get("district").as(String.class), "%"+model.getDistrict()+"%")  );
				}
				

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	//接收文件信息
	private File excelFile;
	//这个setter方法可以接收file的内容
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	private String excelFileFileName;
	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}
	private String excelFileContentType;
	public void setExcelFileContentType(String excelFileContentType) {
		this.excelFileContentType = excelFileContentType;
	}

	/**
	 * excel导入
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	@Action("batchImport")
	public String batchImport() throws Exception{
		/*System.out.println("excel文件:"+excelFile);
		System.out.println("文件名称："+excelFileFileName);
		System.out.println("文件类型："+excelFileContentType);*/
		try {
			//使用POI读取excel文件
			//1)读取工作簿
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
			
			//2)读取工作单
			HSSFSheet sheet = wb.getSheet("area");
			
			//创建List集合，存放所有Area
			List<Area> areaList = new ArrayList<Area>();
			//3)遍历行
			for(Row row:sheet){
				//跳过第一行
				if( row.getRowNum()==0 ){
					continue;
				}
				
				//取出每行的列数据
				String areacode = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//创建Area对象，然后封装列数据
				Area area = new Area();
				area.setAreacode(areacode);
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				
				
				//生成区域简码和城市编码
				//生成区域简码
				String provinceStr = area.getProvince().substring(0, area.getProvince().length()-1);
				String areaStr = area.getCity().substring(0, area.getCity().length()-1);
				String districtStr = area.getDistrict().substring(0, area.getDistrict().length()-1);
				
				//getHeadByString():取每个汉字的拼音首字母
				String[] areaArray = PinYin4jUtils.getHeadByString(provinceStr+areaStr+districtStr);
				String shortcode = PinYin4jUtils.stringArrayToString(areaArray);
				
				//城市编码
				//hanziToPinyin(): 把每个汉字转为拼音
				String citycode = PinYin4jUtils.hanziToPinyin(areaStr);
				//去掉两个拼音之间的空格
				citycode = citycode.replaceAll(" ", "");
				
				area.setShortcode(shortcode.toLowerCase());
				area.setCitycode(citycode);
				
				//放入List集合
				areaList.add(area);
				
			}
			
			//批量保存
			areaService.save(areaList);
			
			//成功
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			//失败
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
	
	/**
	 * excel批量导出
	 * @throws Exception 
	 */
	@Action("batchExport")
	public String batchExport() throws Exception{
		//查询到数据
		//获取到条件对象
		Specification<Area> spec = buildSpecification();
		
		//查询数据库
		List<Area> areaList = areaService.findAll(spec);
		
		//生成excel文件
		//1）创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		//2)创建工作单
		HSSFSheet sheet = wb.createSheet("area");
		
		//创建表头
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("区域编号");
		header.createCell(1).setCellValue("省份");
		header.createCell(2).setCellValue("市");
		header.createCell(3).setCellValue("区");
		header.createCell(4).setCellValue("邮编");
		header.createCell(5).setCellValue("区域简码");
		header.createCell(6).setCellValue("城市编码");
		
		//遍历数据
		for (int i=0;i<areaList.size();i++) {
			
			//创建行
			HSSFRow row = sheet.createRow(i+1);
			
			Area area = areaList.get(i);
			row.createCell(0).setCellValue(area.getAreacode());
			row.createCell(1).setCellValue(area.getProvince());
			row.createCell(2).setCellValue(area.getCity());
			row.createCell(3).setCellValue(area.getDistrict());
			row.createCell(4).setCellValue(area.getPostcode());
			row.createCell(5).setCellValue(area.getShortcode());
			row.createCell(6).setCellValue(area.getCitycode());
			
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
		String fileNAme = format.format(new Date())+".xls";
		//设置下载的弹出框：设置响应头： content-disposition
		response.setHeader("content-disposition", "attachment;filename="+fileNAme);
		
		//把excel内容写出给浏览器用户
		wb.write(response.getOutputStream());
		
		return NONE;
	}

}
