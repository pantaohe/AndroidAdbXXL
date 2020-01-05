package com.pt.utils;

import java.io.File;

import com.pt.config.Constant;

public class Screen {
	public static File getImgFile() {
		File imageFile = new File(Constant.TEMP_PATH + "/screen.png");
		if (imageFile.exists()) {
			imageFile.delete();
		}
		
		Process process = Exec.exec("shell screencap -p /sdcard/ascreen.png");
		Process process2 = Exec.exec("pull /sdcard/screen.png " + Constant.TEMP_PATH);
		if (process == null || process2 == null) {
			System.out.println("图片获取失败，请检查电脑与手机连接和手机设置。");
		}
		return imageFile;
	}
}
