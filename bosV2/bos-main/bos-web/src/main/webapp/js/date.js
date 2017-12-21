//格式化日志

//long->date
//结果为：2017-02-09
function dateFormat(longTypeDate) {
	var dateTypeDate = "";
	//创建日期对象
	var date = new Date();
	//设置时间long类型
	date.setTime(longTypeDate);
	dateTypeDate += date.getFullYear(); // 年
	dateTypeDate += "-" + getMonth(date); // 月
	dateTypeDate += "-" + getDay(date); // 日
	return dateTypeDate;
}


// 返回 01-12 的月份值
function getMonth(date) {
	var month = "";
	month = date.getMonth() + 1; // getMonth()得到的月份是0-11
	//当月份小于10的时候，前面补0
	if (month < 10) {
		month = "0" + month;
	}
	return month;
}

// 返回01-30的日期
function getDay(date) {
	var day = "";
	day = date.getDate();
	//当日期小于10的时候，前面补0
	if (day < 10) {
		day = "0" + day;
	}
	return day;
}