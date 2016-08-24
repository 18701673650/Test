package com.busAPP_IOS.util;

public class Contants {

	// 定义excel文件的路径
	public static final String excelpath = "src/main/resources/busAPP_IOS.xlsx";
	// 用例步骤表单
	// 第一列是序号，下标为0
	public static final int Col_TestCaseID = 0;
	// 第四列是元素类型，下标为3
	public static final int Col_ElementType = 3;
	// 第五列是元素表达式，下标为4
	public static final int Col_ElementID = 4;
	// 第六列是执行的操作，下标为5
	public static final int Col_operation = 5;
	// 第七列是传入的参数，下标为6
	public static final int Col_ParameterID = 6;
	// 第八列是测试结果，下标为7
	public static final int Col_TestStepResult = 7;

	// 用例集合表单
	// 标记是否执行的列，下标为2
	public static final int Col_RunFlag = 2;
	// 第四列是指定表单列，下标为3
	public static final int Col_SheetName = 3;
	// 第五列是测试结果列，下标为4
	public static final int Col_TestSuiteResult = 4;

	//
	public static final String Sheet_TestSet = "set";
	// 指定测试步骤表单名称
	public static final String Sheet_TestSteps = "测试步骤";
	// 指定测试用例集合表单名称
	public static final String Sheet_TestSuite = "用例集合";

}
