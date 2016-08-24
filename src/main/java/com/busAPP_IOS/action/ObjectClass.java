package com.busAPP_IOS.action;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;

public class ObjectClass {
	public static IOSDriver driver;

	// public static WebDriver driver;
	// 初始化driver
	public static void initialization() throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "IOS"); // 手机os
		capabilities.setCapability("platformVersion", "9.3.2"); // 要启动的手机OS版本
		capabilities.setCapability("deviceName", "金鹰的 iPhone"); // 手机类型或模拟器类型，比如MI_2A/Android
																// Emulator/iPhone
																// Simulator
		capabilities.setCapability("udid", "54ed6c5a9b06c73a96b2ce6d74cf7e8e40e479bf"); // 物理机ID
		capabilities.setCapability("app", "com.ipiao.bus"); // 包名
		capabilities.setCapability(CapabilityType.PLATFORM, "Mac"); // 使用的是Mac平台
		capabilities.setCapability("sendKeyStrategy", "setValue"); // 跳过键盘可以输入中文
		// 使用的是Mac平台
		// capabilities.setCapability("app", app.getAbsolutePath());
		// 得到app绝对路径
		// System.out.println(app.getAbsolutePath());
		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

	}

	public static void initialization_() throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "IOS"); // 手机os
		capabilities.setCapability("platformVersion", "9.3.2"); // 要启动的手机OS版本
		capabilities.setCapability("deviceName", "金鹰的 iPhone");
		capabilities.setCapability("autoAcceptAlerts", "true"); // 手机类型或模拟器类型，比如MI_2A/Android

		// Emulator/iPhone
		// Simulator
		capabilities.setCapability("udid", "54ed6c5a9b06c73a96b2ce6d74cf7e8e40e479bf"); // 物理机ID
		capabilities.setCapability("app", "com.piao.cube"); // 包名
		capabilities.setCapability(CapabilityType.PLATFORM, "Mac"); // 使用的是Mac平台
		capabilities.setCapability("sendKeyStrategy", "setValue"); // 跳过键盘可以输入中文
		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	public static void main(String[] args) throws Exception {
		initialization_();
	
	}
}
