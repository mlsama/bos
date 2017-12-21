package com.itheima.test;

import java.util.Arrays;

import org.junit.Test;

import com.itheima.bos.utils.PinYin4jUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * 演示PinYin4J的操作
 * @author lenovo
 *
 */
public class PinYin4JTest {

	@Test
	public void test1() throws Exception{
		//输出格式
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		
		//去除声调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		/**
		 * 汉字转拼音
		 */
		//返回该汉字的所有的多音字  格式： 拼音+声调
		String[] strArray = PinyinHelper.toHanyuPinyinStringArray('长', format);
		for (String str : strArray) {
			System.out.println(str);
		}
	}
	
	
	@Test
	public void test2() throws Exception{
		String province = "广东省";
		String city= "广州市";
		String district = "天河区";
		
		//区域简码： gdgzth
		province = province.substring(0,province.length()-1);
		city = city.substring(0,city.length()-1);
		district = district.substring(0,district.length()-1);
		
		//getHeadByString: 取了每个汉字的首字母
		String[] strArray = PinYin4jUtils.getHeadByString(province+city+district);
		
		//stringArrayToString(): 把一个字符串数组重新构建为一个字符串
		String areacode = PinYin4jUtils.stringArrayToString(strArray);
		
		System.out.println(areacode.toLowerCase());
		
		//城市编码: guangzhou
		String citycode = PinYin4jUtils.hanziToPinyin(city);
		System.out.println(citycode.replaceAll(" ", ""));
	}
	
	
}
