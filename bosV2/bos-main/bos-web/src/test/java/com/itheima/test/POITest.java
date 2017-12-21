package com.itheima.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.itheima.bos.domain.base.Area;

/**
 * 演示POI的读写excel操作
 * @author lenovo
 *
 */
public class POITest {

	/**
	 * 基本读取excel
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test1() throws Exception{
		/**
		 *  1）读取工作簿（WorkBook）
			2）读取工作单（Sheet）
			3）读取行（Row）
			4）读取列（Cell）
		 */
		//1）读取工作簿（WorkBook）
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("E:\\区域测试数据.xls"));
		
		//2）读取工作单（Sheet）
		/*
		wb.getSheet("area");//根据名称获取sheet
		wb.getSheetAt(0);//根据索引获取sheet，从0开始
		*/
		HSSFSheet sheet = wb.getSheetAt(0);
		
		//3）读取行（Row）
		HSSFRow row = sheet.getRow(0); // 根据索引获取行
		
		//4）读取列（Cell）
		System.out.println(row.getCell(0).getStringCellValue());//根据索引获取列
		System.out.println(row.getCell(1).getStringCellValue());//根据索引获取列
		System.out.println(row.getCell(2).getStringCellValue());//根据索引获取列
		System.out.println(row.getCell(3).getStringCellValue());//根据索引获取列
		System.out.println(row.getCell(4).getStringCellValue());//根据索引获取列
		
		
		
	}
	
	
	/**
	 * 遍历读取excel
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test2() throws Exception{
		/**
		 *  1）读取工作簿（WorkBook）
			2）读取工作单（Sheet）
			3）读取行（Row）
			4）读取列（Cell）
		 */
		//1）读取工作簿（WorkBook）
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("E:\\区域测试数据.xls"));
		
		//2）读取工作单（Sheet）
		HSSFSheet sheet = wb.getSheetAt(0);
		
		//3)遍历所有行
		for(Row row : sheet){
			//跳过第一行
			//获取当前行索引，从0开始
			if(row.getRowNum()==0){
				continue;
			}
			
			System.out.print(row.getCell(0).getStringCellValue()+"\t");
			System.out.print(row.getCell(1).getStringCellValue()+"\t");
			System.out.print(row.getCell(2).getStringCellValue()+"\t");
			System.out.print(row.getCell(3).getStringCellValue()+"\t");
			System.out.print(row.getCell(4).getStringCellValue());
			
			System.out.println();
		}
		
	}
	
	/**
	 * 写出excel
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception{
		/**
		 * 1）创建工作簿（WorkBook）
			2）创建工作单（Sheet）
			3）创建行（Row）
			4）创建列（Cell）
		 */
		//1）创建工作簿（WorkBook）
		HSSFWorkbook wb = new HSSFWorkbook();
		
		//2）创建工作单（Sheet）
		HSSFSheet sheet = wb.createSheet("工作单1");
		
		//3）创建行（Row）
		HSSFRow row = sheet.createRow(0);
		
		//4）创建列（Cell）
		row.createCell(0).setCellValue("省份");
		row.createCell(1).setCellValue("城市");
		row.createCell(2).setCellValue("区");
		row.createCell(3).setCellValue("邮编");
		
		//5)写出文件
		wb.write(new FileOutputStream("e:\\area.xls"));
	}
	
	
	/**
	 * 写出excel（利用数据写出行）
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception{
		//1）创建工作簿（WorkBook）
		HSSFWorkbook wb = new HSSFWorkbook();
		
		//2）创建工作单（Sheet）
		HSSFSheet sheet = wb.createSheet("工作单1");
		
		//3）创建行（Row）
		HSSFRow row = sheet.createRow(0);
		
		//4）创建列（Cell）
		row.createCell(0).setCellValue("省份");
		row.createCell(1).setCellValue("城市");
		row.createCell(2).setCellValue("区");
		row.createCell(3).setCellValue("邮编");
		
		//模拟从数据库读取数据
		List<Area> areaList = new ArrayList<Area>();
		Area a1 = new Area();
		a1.setProvince("广东省");
		a1.setCity("广州市");
		a1.setDistrict("天河区");
		a1.setPostcode("510001");
		
		Area a2 = new Area();
		a2.setProvince("广东省");
		a2.setCity("广州市");
		a2.setDistrict("番禺区");
		a2.setPostcode("510002");
		
		Area a3 = new Area();
		a3.setProvince("广东省");
		a3.setCity("广州市");
		a3.setDistrict("越秀区");
		a3.setPostcode("510003");
		
		areaList.add(a1);
		areaList.add(a2);
		areaList.add(a3);
		
		//遍历数据，创建行和列
		for(int i=0;i<areaList.size();i++){
			//创建行
			Area area = areaList.get(i);
			
			HSSFRow areaRow = sheet.createRow(i+1);
			
			//创建列
			areaRow.createCell(0).setCellValue(area.getProvince());
			areaRow.createCell(1).setCellValue(area.getCity());
			areaRow.createCell(2).setCellValue(area.getDistrict());
			areaRow.createCell(3).setCellValue(area.getPostcode());
			
		}
		
		
		//5)写出文件
		wb.write(new FileOutputStream("e:\\area.xls"));
	}
}
