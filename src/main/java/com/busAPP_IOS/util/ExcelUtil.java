package com.busAPP_IOS.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.busAPP_IOS.action.ObjectClass;
import com.busAPP_IOS.run.RunTestCase;

import io.appium.java_client.TouchAction;

public class ExcelUtil {

	// 定义获取工作薄
	private static XSSFWorkbook ExcelWbook;
	// 定义获取工作表
	private static XSSFSheet ExcelSheet;
	// 定义获取表格的行
	private static XSSFRow ExcelRow;
	// 定义获取表格的列
	private static XSSFCell ExcelCell;

	/**
	 * 设定要操作的表格文件
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void setExcelFile(String path) throws Exception {

		FileInputStream ExcelFile;
		try {
			ExcelFile = new FileInputStream(path);
			// 实例化excelWorkbook对象
			ExcelWbook = new XSSFWorkbook(ExcelFile);
			ExcelFile.close();
		} catch (IOException e) {

			// 定义结果为false
			System.out.println("setExcelFile");
			RunTestCase.testResult = false;
			System.out.println(e.toString());
			System.out.println("读取Excel文件出错");
			Log.info("错误信息" + e.toString());
		}
	}

	// 获取sheet数量
	public static int SheetNum(String path) {
		FileInputStream ExcelFile;
		try {
			ExcelFile = new FileInputStream(path);
			// 实例化excelWorkbook对象
			ExcelWbook = new XSSFWorkbook(ExcelFile);
			ExcelFile.close();
			return ExcelWbook.getNumberOfSheets();
		} catch (IOException e) {

			// 定义结果为false
			System.out.println("setExcelFile");
			RunTestCase.testResult = false;
			System.out.println(e.toString());
			System.out.println("读取Excel文件出错");
			Log.info("错误信息" + e.toString());
			return 0;
		}

	}

	// 获取文件流
	public static XSSFWorkbook getIO(String path) {
		FileInputStream ExcelFile;
		try {
			ExcelFile = new FileInputStream(path);
			// 实例化excelWorkbook对象
			ExcelWbook = new XSSFWorkbook(ExcelFile);
			ExcelFile.close();
			return ExcelWbook;
		} catch (IOException e) {

			// 定义结果为false
			System.out.println("setExcelFile");
			RunTestCase.testResult = false;
			System.out.println(e.toString());
			System.out.println("读取Excel文件出错");
			Log.info("错误信息" + e.toString());
			return null;
		}
	}

	/**
	 * 在读/写Excel文件的时候，设定要操作的excel文件和sheet名称
	 * 
	 * @param path
	 * @param sheetName
	 * @throws Exception
	 */
	public static void setExcelFile(String path, String sheetName) throws Exception {

		FileInputStream ExcelFile;
		try {
			ExcelFile = new FileInputStream(path);

			// 实例化ExcelWorkbook对象
			ExcelWbook = new XSSFWorkbook(ExcelFile);
			// 实例化ExcelSheet对象
			ExcelSheet = ExcelWbook.getSheet(sheetName);
		} catch (IOException e) {
			System.out.println("setExcelFile");
			RunTestCase.testResult = false;
			System.out.println(e.toString());
			System.out.println("读取Excel文件出错");
			Log.info("错误信息" + e.toString());
			throw (e);
		}
	}

	/**
	 * 读取指定的单元格的函数
	 * 
	 * @param SheetName
	 * @param RowNum
	 * @param ColNum
	 * @return
	 * @throws Exception
	 */
	public static String getCellData(String SheetName, int RowNum, int ColNum) throws Exception {

		// 定义表单名称
		ExcelSheet = ExcelWbook.getSheet(SheetName);
		try {
			// 通过函数指定单元格的列号和行号，获取指定单元格内容
			ExcelCell = ExcelSheet.getRow(RowNum).getCell(ColNum);
			// 如果单元格内容是字符串用getStringCellValue
			// 如果单元格内容是数字用getNumericCellValue
			String CellData = ExcelCell.getCellType() == XSSFCell.CELL_TYPE_STRING
					? ExcelCell.getStringCellValue() + " "
					: String.valueOf(Math.round(ExcelCell.getNumericCellValue()));
			return CellData;
		} catch (Exception e) {
			RunTestCase.testResult = false;
			System.out.println("getCellData");
			System.out.println(e.toString());
			Log.info("错误信息" + e.toString());
			return "读取表格内容失败";
		}
	}

	/**
	 * 获取Excel文件最后一行行号
	 * 
	 * @param SheetName
	 * @return
	 */
	public static int getLastRowNum(String SheetName) {
		ExcelSheet = ExcelWbook.getSheet(SheetName);
		return ExcelSheet.getLastRowNum();
	}

	/**
	 * 获取Excel文件总行数
	 * 
	 * @param SheetName
	 * @return
	 */
	public static int getRowCount(String SheetName) {
		ExcelSheet = ExcelWbook.getSheet(SheetName);
		int number = ExcelSheet.getLastRowNum();
		return number;
	}

	/**
	 * 从Excel文件指定的表单中，获取第一次包含的测试用例序号的行号
	 * 
	 * @param SheetName
	 * @param testCaseName
	 * @param colName
	 * @return
	 * @throws Exception
	 */
	public static int getFireRowContainsTestCaseID(String SheetName, String testCaseName, int colName)
			throws Exception {

		int i;
		try {
			ExcelSheet = ExcelWbook.getSheet(SheetName);
			// 获取所有行数
			int rowCount = ExcelUtil.getRowCount(SheetName);
			// 使用for循环遍历所有行，判断第一次包含测试用例集合中用例名称的行号
			for (i = 0; i < rowCount; i++) {

				if ((ExcelUtil.getCellData(SheetName, i, colName).trim()).equalsIgnoreCase((testCaseName.trim()))) {

					// 如果包含，退出循环并返回测试用例序号关键字的行号
					break;
				}
			}
			return i;
		} catch (Exception e) {
			System.out.println("getFireRowContainsTestCaseID");
			return 0;
		}
	}

	/**
	 * 获取指定Sheet中某个测试用例步数的个数
	 * 
	 * @param SheetName
	 * @param testCaseID
	 * @param testCaseStartNumber
	 * @return
	 */
	public static int getTestCaseLastStepRow(String SheetName, String testCaseID, int testCaseStartNumber) {
		try {
			ExcelSheet = ExcelWbook.getSheet(SheetName);

			// 从包含指定测试用例序号的第一行开始遍历，直到某一行不出现指定册数用例序号
			for (int i = testCaseStartNumber; i <= ExcelUtil.getRowCount(SheetName) - 1; i++) {
				if (!testCaseID.trim().equals((ExcelUtil.getCellData(SheetName, i, Contants.Col_TestCaseID).trim()))) {
					int number = i;
					return number;
				}

			}
			int number = ExcelSheet.getLastRowNum() + 1;
			return number;
		} catch (Exception e) {
			Log.info("错误信息" + e.toString());
			RunTestCase.testResult = false;
			return 0;
		}
	}

	/**
	 * 在Excel文件的执行单元格中写入的数据
	 * 
	 * @param SheetName
	 * @param RowNum
	 * @param ColNum
	 * @param Result
	 */
	public static void setCellData(String SheetName, int RowNum, int ColNum, String Result) {
		ExcelSheet = ExcelWbook.getSheet(SheetName);
		try {
			// 获取Excel文件中的行对象
			ExcelRow = ExcelSheet.getRow(RowNum);
			// 如果单元格为空，则返回null
			ExcelCell = ExcelRow.getCell(ColNum, ExcelRow.RETURN_BLANK_AS_NULL);

			if (ExcelCell == null) {
				// 当单元格是null的时候创建单元格
				// 当单元格为空，无法直接调用单元格对象的setCellValue方法设定单元格的值
				ExcelCell = ExcelRow.getCell(ColNum);
				// 创建单元格后可以调用单元格对象的setCellValue方法设定单元格的值
				ExcelCell.setCellValue(Result);
			} else {
				// 单元格中有内容，直接调用单元格对象的setCellValue方法设定单元格的值
				ExcelCell.setCellValue(Result);
			}

			// 实例写入Excel文件的文件输出流对象
			FileOutputStream fileOut = new FileOutputStream(Contants.excelpath);
			// 将内容写入Excel文件中
			ExcelWbook.write(fileOut);
			// 调用flush方法强制刷新写入文件
			fileOut.flush();
			// 关闭输出流对象
			fileOut.close();
		} catch (Exception e) {
			Log.info("错误信息" + e.toString());
			RunTestCase.testResult = false;
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 通过excel里的类型返回应用的类型
	 * 
	 * @param CellData5
	 * @param Element5
	 * @param CellData
	 * @param Element
	 */
	public static void getElementType(String elementtype, String element, String operation, String Parameter) {
		try {

			if (elementtype.equals("xpath") || elementtype.equals("id") || elementtype.equals("linktext")
					|| elementtype.equals("class") || elementtype.equals("partial") || elementtype.equals("name")) {

				WebElement element_ = FindElement(elementtype, element);
				Sendkey(element_, operation, Parameter);

				return;
				// Log.info("------------------------使用的元素类型是:--"+CellData5+"--使用的元素表达式是:--"+Element5+"--执行的操作是:--"+CellData+"--使用的参数是:--"+Element+"--");
			} else if (elementtype.equals("jQuery")) {

				((JavascriptExecutor) ObjectClass.driver).executeScript(element);
				Log.info("------------------------使用--" + operation + "--方法执行--" + Parameter + "--操作");

				return;

			} else if (elementtype.toLowerCase().equals("get")) {

				ObjectClass.driver.get(Parameter);
				Log.info("------------------------使用--" + operation + "--方法执行--" + Parameter + "--操作");

				return;
			} else if (elementtype.toLowerCase().equals("firefox")) {

				// ObjectClass.driver = new FirefoxDriver();
				Log.info("------------------------使用--" + operation + "--方法执行--" + Parameter + "--操作");

				return;
			} else if (elementtype.trim().toLowerCase().equals("assert")) {

				String alert = FindElement(operation, Parameter).getText();
				System.out.println(alert);
				Assert.assertEquals(element, alert);
				System.out.println("------------------------使用--" + element + "--判断--" + alert + "--操作");
				Log.info("------------------------使用--" + element + "--判断--" + alert + "--操作");
				return;

			} else if (elementtype.trim().toLowerCase().equals("initialization")) {

				ObjectClass.initialization();
				Log.info("------------------------使用--" + element + "--判断--" + "initialization" + "--操作");
				return;

			} else if (elementtype.trim().toLowerCase().equals("公共方法")) {
				System.out.println("公共方法");
				return;

			} else if (elementtype.trim().toLowerCase().equals("sleep")) {
				Thread.sleep(Integer.parseInt(element) * 1000);

			} else if (elementtype.trim().toLowerCase().equals("坐标")) {
				int width = ObjectClass.driver.manage().window().getSize().width;
				int height = ObjectClass.driver.manage().window().getSize().height;

				new TouchAction(ObjectClass.driver).press(width / 4 * 3, height / 20 * 11).release().perform();
				return;

			} else if (elementtype.trim().toLowerCase().equals("滑动")) {
				int width = ObjectClass.driver.manage().window().getSize().width;
				int height = ObjectClass.driver.manage().window().getSize().height;
				ObjectClass.driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 500);
				return;

			} else if (elementtype.trim().toLowerCase().equals("quit")) {
				ObjectClass.driver.quit();
				return;
			} else if (elementtype.trim().toLowerCase().equals("initialization_")) {
				ObjectClass.initialization_();
				Log.info("----------初始化");
				return;
			} else {
				System.out.println("元素类型不对");
			}

		} catch (Exception e) {
			RunTestCase.testResult = false;
			Log.info("错误信息" + e.toString());
			System.out.println(e.toString());
			System.out.println("getElementType");
			System.out.println("读取表格内容失败");
		}
	}

	/**
	 * 解析这个元素通过什么方式获取
	 * 
	 * @param CellData
	 * @param Element
	 * @return
	 * @throws Exception
	 */
	public static WebElement FindElement(String CellData, String Element) {
		try {
			Thread.sleep(500);
			// 根据locatorTyoe判断返回的By类型定位方式
			if (CellData.toLowerCase().trim().equals("xpath")) {
				return ObjectClass.driver.findElement(By.xpath(Element));
			} else if (CellData.toLowerCase().trim().equals("id")) {
				return ObjectClass.driver.findElement(By.id(Element));
			} else if ((CellData.toLowerCase().trim().equals("linktext"))
					|| (CellData.toLowerCase().trim().equals("link"))) {
				return ObjectClass.driver.findElement(By.linkText(Element));
			} else if ((CellData.toLowerCase().trim().equals("class"))
					|| (CellData.toLowerCase().trim().equals("classname"))) {
				return ObjectClass.driver.findElement(By.className(Element));
			} else if (CellData.toLowerCase().trim().equals("partial")) {
				return ObjectClass.driver.findElement(By.partialLinkText(Element));
			} else if (CellData.toLowerCase().trim().equals("name")) {
				return ObjectClass.driver.findElement(By.name(Element));
			} else {
				throw new Exception("输入的locator Type 未在程序中被定义" + CellData);
			}
		} catch (Exception e) {
			Log.info("错误信息" + e.toString());
			System.out.println(e.toString());
			RunTestCase.testResult = false;
			return null;
		}
	}

	/**
	 * 通过操作方式关键字返回操作方式的方法
	 * 
	 * @param element
	 * @param parameter
	 * @param string
	 * @throws Exception
	 */
	public static void Sendkey(WebElement element, String parameter, String string) throws Exception {
		Thread.sleep(500);
		if (parameter.trim().toLowerCase().equals("click")) {
			element.click();
		} else if (parameter.trim().toLowerCase().equals("sendkeys")) {
			if (string.equals("身份证号")) {
				element.sendKeys(CardId.generate());
			} else {
				element.sendKeys(string);
			}

		} else if (parameter.trim().toLowerCase().equals("clear")) {
			element.clear();
		} else {
			Log.info("Sendkey方法提示:   没有匹配的操作方法");
			System.out.println("没有匹配的操作方法");
		}
	}

}
