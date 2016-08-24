package com.busAPP_IOS.run;

import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.busAPP_IOS.action.ObjectClass;
import com.busAPP_IOS.util.Contants;
import com.busAPP_IOS.util.ExcelUtil;
import com.busAPP_IOS.util.FilePath;
import com.busAPP_IOS.util.Log;

public class RunTestCase {

	// 定义记录测试结果的变量
	public static boolean testResult;
	// 根据相对路径获取excel的绝对路径
	public static String excel_path = new File(Contants.excelpath).getAbsolutePath();

	@Test(description = "商户普通买票退票", priority = 0, threadPoolSize = 1, invocationCount = 1, successPercentage = 100)
	public void senic_order() throws Exception {
		runTestCase(1);
	}

	@Test(description = "商户演艺买票退票", priority = 1, threadPoolSize = 1, invocationCount = 1, successPercentage = 100)
	public void acting_senic_order() throws Exception {
		runTestCase(2);
	}
	
	@Test(description = "商户通用买票退票", priority = 2,threadPoolSize = 1,invocationCount = 1, successPercentage = 100)
	public void universal_scenic_order() throws Exception {
		runTestCase(3);
	}
	
	@Test(description = "导游普通买票退票", priority = 3,threadPoolSize = 1,invocationCount = 1, successPercentage = 100)
	public void cube_senic_order() throws Exception{
		runTestCase(4);
	}

	@Test(description = "导游演艺买票退票", priority = 4,threadPoolSize = 1,invocationCount = 1, successPercentage = 100)
	public void cube_acting_senic_order() throws Exception{
		runTestCase(5);
	}
	
	@Test(description = "导游通用买票退票", priority = 5,threadPoolSize = 1,invocationCount = 1, successPercentage = 100)
	public void cube_universal_scenic_order() throws Exception{
		runTestCase(6);
	}

	
	
	public void runTestCase(int testCaseNO) throws Exception {

		ExcelUtil.setExcelFile(excel_path);
		// 读取测试用例集合表单中的总行数
		int testCaseCount = ExcelUtil.getRowCount(Contants.Sheet_TestSuite);
		// 使用for循环，执行所有标记为Y的用例
		// for (int testCaseNO = 1; testCaseNO <= testCaseCount; testCaseNO++) {
		// 读取“测试用例集合”Sheet中每行的测试用例号
		String testCaseID = ExcelUtil.getCellData(Contants.Sheet_TestSuite, testCaseNO, Contants.Col_TestCaseID);
		// 读取“测试用例集合”Sheet中每行是否运行的值
		String testCaseRunFlag = ExcelUtil.getCellData(Contants.Sheet_TestSuite, testCaseNO, Contants.Col_RunFlag);

		// 如果运行值为“y“则执行测试用例中得所有步骤
		// if (testCaseRunFlag.trim().equals("y")) {
		Log.startTestCase(testCaseID);
		// 设定测试用例的当前结果为true，即表明测试成功

		// 获取指定的表单名称
		String sheet = ExcelUtil.getCellData(Contants.Sheet_TestSuite, testCaseNO, Contants.Col_SheetName);
		String sheetname = null;
		// if(sheet.trim().equals("测试步骤")){
		// sheetname = Contants.Sheet_TestSteps;
		// }else if(sheet.trim().equals("set")){
		// sheetname = Contants.Sheet_TestSet;
		// }
		// 通过现在获取到的表格名称对比所有的表单名称，如果equals，则把表单名字赋值给sheetname
		for (int sheetName = 0; sheetName < ExcelUtil.SheetNum(excel_path); sheetName++) {

			if (sheet.trim().equals(ExcelUtil.getIO(excel_path).getSheetName(sheetName))) {

				sheetname = ExcelUtil.getIO(excel_path).getSheetName(sheetName);
				break;

			}
		}
		testResult = true;
		// 在Sheet中获取当前要执行的第一个步骤所在行
		int testStep = ExcelUtil.getFireRowContainsTestCaseID(sheetname, testCaseID, Contants.Col_TestCaseID);
		// 在Sheet中获取当前要执行的最后一个步骤所在行
		int testLastStep = ExcelUtil.getTestCaseLastStepRow(sheetname, testCaseID, testStep);
		action(sheetname, testStep, testLastStep);
		if (testResult == false) {
			// 如果测试用例中得任何一个测试步骤执行失败，则测试用例集合中当前测试用例的执行结果设定为“测试执行失败”
			ExcelUtil.setCellData(Contants.Sheet_TestSuite, testCaseNO, Contants.Col_TestSuiteResult, "测试执行失败");

			// 在log日志中打印测试用例执行完毕
			System.out.println("测试执行失败");
			FilePath.takeTakesScreenshot();
			logout();

		}
		if (testResult == true) {
			// 如果测试用例的所有步骤都执行成功，则测试用例集合中当前测试用例的执行结果设定为“测试执行成功”
			ExcelUtil.setCellData(Contants.Sheet_TestSuite, testCaseNO, Contants.Col_TestSuiteResult, "测试执行成功");
			System.out.println("测试执行成功");
		}

		Log.endTestCase(testCaseID);
	}
	// }

	// }

	private static void action(String sheet, int testStep, int testLastStep) {
		try {
			String elementtype, element, operation, Parameter;
			// String test = "公共方法";
			String Sheetname = "";

			// 遍历所有测试用例步骤
			for (; testStep < testLastStep; testStep++) {

				// 获取元素类型
				elementtype = ExcelUtil.getCellData(sheet, testStep, Contants.Col_ElementType).trim();

				// 获取元素表达式
				element = ExcelUtil.getCellData(sheet, testStep, Contants.Col_ElementID).trim();

				// 获取元素操作
				operation = ExcelUtil.getCellData(sheet, testStep, Contants.Col_operation).trim();

				// 获取传入的参数
				Parameter = ExcelUtil.getCellData(sheet, testStep, Contants.Col_ParameterID).trim();
				// if(elementtype.trim().equals(test)){
				// publicSheet(test, element);
				// }

				// 通过每个步骤的第四个表格判断是不是一个公共方法，如果是就跳进过去通过第五列作为用例标记
				for (int sheetName = 0; sheetName < ExcelUtil.SheetNum(excel_path); sheetName++) {

					if (elementtype.trim().equals(ExcelUtil.getIO(excel_path).getSheetName(sheetName))) {
						Sheetname = ExcelUtil.getIO(excel_path).getSheetName(sheetName);
						publicSheet(Sheetname, element);
						break;
					}
				}

				System.out.println("表单名称---" + sheet + "---元素类型---" + elementtype + "---元素表达式---" + element
						+ "---元素操作---" + operation + "---传入的参数---" + Parameter + "---");
				Log.info("表单名称---" + sheet + "---元素类型---" + elementtype + "---元素表达式---" + element + "---元素操作---"
						+ operation + "---传入的参数---" + Parameter + "---");
				ExcelUtil.getElementType(elementtype, element, operation, Parameter);

				if (testResult == true) {
					// 当前测试步骤执行成功，在“发送邮件”Sheet中，会将当前执行的步骤结果设定为“测试步骤执行成功”
					ExcelUtil.setCellData(sheet, testStep, Contants.Col_TestStepResult, "测试步骤执行成功");
				} else {
					// 当前测试步骤执行失败，在“发送邮件”Sheet中，将当前执行的步骤结果设定为“测试步骤执行失败"
					ExcelUtil.setCellData(sheet, testStep, Contants.Col_TestStepResult, "测试步骤执行失败");
					break;
				}
			}

		} catch (Exception e) {
			testResult = false;
			Log.info(e.toString());
			System.out.println(e);
		}
	}

	// 执行公共表单
	public static void publicSheet(String sheetname, String element) {
		try {
			String elementtype_, element_, operation_, Parameter_;
			// 在Sheet中获取当前要执行的第一个步骤所在行
			int testStep_ = ExcelUtil.getFireRowContainsTestCaseID(sheetname, element, Contants.Col_TestCaseID);

			// 在Sheet中获取当前要执行的最后一个步骤所在行
			int testLastStep_ = ExcelUtil.getTestCaseLastStepRow(sheetname, element, testStep_);

			// 读取公共方法中的用例
			for (; testStep_ < testLastStep_; testStep_++) {

				// 获取元素类型
				elementtype_ = ExcelUtil.getCellData(sheetname, testStep_, Contants.Col_ElementType).trim();

				// 获取元素表达式
				element_ = ExcelUtil.getCellData(sheetname, testStep_, Contants.Col_ElementID).trim();

				// 获取元素操作
				operation_ = ExcelUtil.getCellData(sheetname, testStep_, Contants.Col_operation).trim();

				// 获取传入的参数
				Parameter_ = ExcelUtil.getCellData(sheetname, testStep_, Contants.Col_ParameterID).trim();

				System.out.println("表单名称---" + sheetname + "---元素类型---" + elementtype_ + "---元素表达式---" + element_
						+ "---元素操作---" + operation_ + "---传入的参数---" + Parameter_ + "---");
				Log.info("表单名称---" + sheetname + "---元素类型---" + elementtype_ + "---元素表达式---" + element_ + "---元素操作---"
						+ operation_ + "---传入的参数---" + Parameter_ + "---");
				ExcelUtil.getElementType(elementtype_, element_, operation_, Parameter_);
				if (testResult == true) {
					// 当前测试步骤执行成功，在“发送邮件”Sheet中，会将当前执行的步骤结果设定为“测试步骤执行成功”
					ExcelUtil.setCellData(sheetname, testStep_, Contants.Col_TestStepResult, "测试步骤执行成功");
				} else {
					// 当前测试步骤执行失败，在“发送邮件”Sheet中，将当前执行的步骤结果设定为“测试步骤执行失败"
					ExcelUtil.setCellData(sheetname, testStep_, Contants.Col_TestStepResult, "测试步骤执行失败");
					break;
				}

			}
		} catch (Exception e) {
			Log.info(e.toString());
			testResult = false;
		}
	}

	/**
	 *  指定执行失败后的退出登录的方法
	 */

	void logout() {
		for(int i = 0;i < 6; i ++){
			try {
				Thread.sleep(2000);
				ObjectClass.driver.findElement(By.name("首页")).click();
				ObjectClass.driver.findElement(By.name("我的@2x.png")).click();
				ObjectClass.driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAImage[1]/UIAButton[1]"))
						.click();
				int width = ObjectClass.driver.manage().window().getSize().width;
				int height = ObjectClass.driver.manage().window().getSize().height;
				ObjectClass.driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 500);
				ObjectClass.driver.findElement(By.name("退出登录")).click();
				ObjectClass.driver.quit();
			} catch (Exception e) {
				ObjectClass.driver
				.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[2]"))
				.click();
				continue;
			}
		}
	}

	@BeforeClass
	public void beforClass() {
		// 指定使用的log4j文件
		DOMConfigurator.configure("log4j.xml");

	}

	// @AfterClass
	// public void afertClass() {
	// System.out.println("执行退出的方法");
	//
	// }
}
