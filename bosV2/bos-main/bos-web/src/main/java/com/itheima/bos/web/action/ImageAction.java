package com.itheima.bos.web.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.service.PromotionService;

/**
 * 图片处理的Action
 * @author lenovo
 *
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/image")
public class ImageAction extends BaseAction<Object>{

	@Override
	protected Specification<Object> buildSpecification() {
		return null;
	}
	
	//提供setter方法来接收文件信息
	//文件内容
	private File imgFile;
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	//文件名称
	private String imgFileFileName;
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	/**
	 * 图片上传
	 * @throws Exception 
	 */
	@Action("upload")
	public String upload() throws Exception{
		try {
			//System.out.println(imgFile);
			//System.out.println(imgFileFileName);
			
			//需求：把上传的图片放在webapp的upload目录
			//获取项目的upload目录的绝对路径
			//使用ServletContext的getRealPath():获取项目里面的文件或目录的绝对路径
			String uploadPath = ServletActionContext.getServletContext().getRealPath("/upload");
			System.out.println(uploadPath);
			
			//生成唯一的随机文件名称 : uuid
			String uuid = UUID.randomUUID().toString();
			//获取文件的后缀名  .jpg
			String extName = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
			//新的文件名
			String fileName = uuid+extName;
			/**
			 * 参数一：源文件
			 * 参数二：目标文件（拷贝到的地方）
			 */
			FileUtils.copyFile(imgFile, new File(uploadPath+"/"+fileName));
			
			
			//使用ServletContext.getContexPath(): 获取文件的项目的相对路径    /bos-web/upload/dfff.jpg
			String fileContextPath = ServletActionContext.getServletContext().getContextPath()+"/upload/"+fileName;
			
			System.out.println(fileContextPath);
			
			//上传成功
			result.put("error", 0);
			//返回上传成功后目标文件的路径(项目的相对路径就可以啦)
			result.put("url", fileContextPath);
		} catch (Exception e) {
			e.printStackTrace();
			//上传失败
			result.put("error", 1);
			result.put("message", e.getMessage());
		}
		return writeJson(result);
	}
	
	/**
	 * 查询文件，返回给图片空间
	 * @throws Exception 
	 */
	@Action("manager")
	public String manager() throws Exception{
		//1.找到upload目录(绝对路径)
		String uploadPath = ServletActionContext.getServletContext().getRealPath("/upload");
		
		File uploadFile = new File(uploadPath);
		
		//2.获取upload目录里面的所有文件
		//遍历目录取的文件信息
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(uploadFile.listFiles() != null) {
			for (File file : uploadFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				//判断是否为目录
				if(file.isDirectory()) {
					//目录
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					//文件
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		
		String current_url = ServletActionContext.getServletContext().getContextPath()+"/upload/";
		
		//3.把文件信息（文件名，更新时间，后缀名等）返回成json格式数据给KindEditor
		//返回所有文件信息列表
		result.put("file_list", fileList);
		//返回文件所在的项目的路径
		result.put("current_url", current_url);
		
		return writeJson(result);
	}
	

}
