package com.pt.config;

public class Parameter {
	public static String SYS_PATH = System.getProperty("user.dir").replace("\\", "/");
	public static String CMD_PATH = "cmd /c " + SYS_PATH;
	
	static{
		String system = System.getProperty("os.name");
		if (system.contains("Windows")) {
			CMD_PATH = CMD_PATH + "/resources/Windows/adb ";
		} else if (system.contains("Mac")) {
			CMD_PATH = CMD_PATH + "/resources/Mac/adb ";
		} else {
			System.out.println("该程序暂不支持" + system);
		}
	}
	
	
}
