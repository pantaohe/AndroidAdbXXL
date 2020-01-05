package com.pt.utils;

import com.pt.config.Parameter;

public class Exec {

	public static Process exec(String par) {
		Process process = null;
		try {
			String cmd = Parameter.CMD_PATH + par;
			process = Runtime.getRuntime().exec(cmd);
			int result = process.waitFor();
            if (result != 0) {
                System.out.println("Failed to execute \"" + cmd + "\", result code is " + result);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return process;
	}

}
