package com.busAPP_IOS.util;

public class DateUtil {

	// 本类主要用于生成年月日时分秒的信息，用于生成保存截图的文件目录名和文件名
	public String format(java.util.Date date, String format) {
		/*
		 * 格式化输出日期
		 * 
		 * @return 返回字符型日期
		 */
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 返回年份
	 * 
	 * @return 返回年份
	 */
	public int getYear(java.util.Date date) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/*
	 * 返回月份
	 * 
	 * @return 返回月份
	 */
	public int getMonth(java.util.Date date) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONDAY) + 1;
	}

	/*
	 * 返回月份的第几天
	 * 
	 * @return 返回月份的第几天
	 */
	public int getDay(java.util.Date date) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/*
	 * 返回小时
	 * 
	 * @param date 日期
	 * 
	 * @return 返回小时
	 */
	public int getHour(java.util.Date date) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/*
	 * 返回分钟
	 * 
	 * @param date 日期
	 * 
	 * @return 返回分钟
	 */
	public int getMinute(java.util.Date date) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/*
	 * 返回秒
	 * 
	 * @param date 日期
	 * 
	 * @return 返回秒
	 */
	public int getSecond(java.util.Date date) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

}
