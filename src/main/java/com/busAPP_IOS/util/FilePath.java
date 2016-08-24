package com.busAPP_IOS.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.busAPP_IOS.action.ObjectClass;
public class FilePath {

	public static String suppliermap = "/masterSupplier.properties";
	public static String parameter = "/parameter.properties";
	public static String scenicimg = "src/test/resources/scenic_img.png";
	public static String scenicSpot_name = "景点名称" + CardId.getRandomJianHan(3);

	/**
	 * 返回图片的绝对路径
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String imgpath() throws Exception {

		File imgpath = new File(scenicimg);

		return imgpath.getAbsolutePath();
	}

	/**
	 * 声明截图的方法
	 */
	public static void takeTakesScreenshot() {
		DateUtil dateutil = new DateUtil();
		try {
			// 生成日期对象
			Date date = new Date();
			// 调用生成截图所在的文件夹日期名称
			String picDir = "src/screenshot/" + String.valueOf(dateutil.getYear(date)) + "-"
					+ String.valueOf(dateutil.getMonth(date)) + "-" + String.valueOf(dateutil.getDay(date));
			if (!new File(picDir).exists()) {
				createDir(picDir);
			}
			// 调用生成截图文件的时间名称
			String file_path = picDir + "/"
					+ String.valueOf(dateutil.getHour(date) + "-" + String.valueOf(dateutil.getMinute(date)) + "-"
							+ String.valueOf(dateutil.getSecond(new Date())) + ".png");
			// 进行截图,并将文件内容保存在srcFile对象中
			File srcFile = ((TakesScreenshot) ObjectClass.driver).getScreenshotAs(OutputType.FILE);
			// 将截图文件内容写入到磁盘中，生成截图文件
			FileUtils.copyFile(srcFile, new File(file_path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createFile(String dastFilName) {

		File file = new File(dastFilName);
		if (file.exists()) {
			System.out.println("创建单个文件" + dastFilName + "失败");
			return false;
		}
		if (dastFilName.endsWith(File.separator)) {
			System.out.println("创建单个文件" + dastFilName + "失败");
			return false;
		}

		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在的目录不存在，马上创建-----------");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {

				System.out.println("创建单个文件" + dastFilName + "成功!");
				return true;
			} else {
				System.out.println("创建单个文件" + dastFilName + "失败!");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + dastFilName + "失败!" + e.getMessage());
			return false;

		}

	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已存在!");
			return false;
		}

		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功!");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败!");
			return false;
		}
	}

}
