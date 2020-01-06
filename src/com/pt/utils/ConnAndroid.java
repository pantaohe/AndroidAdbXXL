package com.pt.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ConnAndroid {
	
	public static int[] dpi() {
		File imageFile = Screen.getImgFile();
		int screenWidth = 0;
		int screenHeight = 0;
		try {
			try {
				BufferedImage image = ImageIO.read(imageFile);
				screenWidth = image.getWidth();
				screenHeight = image.getHeight();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("手机分辨率检测失败，请检查电脑与手机连接和手机设置。");
			}
			if (screenWidth == 0 || screenHeight == 0) {
				System.out.println("手机分辨率检测失败，请检查电脑与手机连接和手机设置。");
			}
			System.out.println("检测到您的手机分辨率为" + screenWidth + "x" + screenHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new int[]{screenWidth, screenHeight};
	}
	
	public static void connAnd(){
		ArrayList<String> devices = getConnectedDevices();
		if (devices.size() == 1) {
			System.out.println("检测到您连接的手机为：" + devices.get(0) + "\n");
		} else {
			if (devices.size() == 0) {
				System.out.println("未检测到已连接的手机，等待手机连接...");
			} else {
				System.out.println("检测到已连接多部手机，请移除多余手机，只留下一台...");
			}
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				devices = getConnectedDevices();
				if (devices.size() == 1) {
					break;
				}
			}
			System.out.println("\n检测到您连接的手机为：" + devices.get(0) + "\n");
		}
	}

	public static ArrayList<String> getConnectedDevices() {
		ArrayList<String> devices = new ArrayList<>();
		try {

			Process process = Exec.exec("devices");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			boolean startCount = false;
			String deviceName;
			while ((line = reader.readLine()) != null) {
				if (line.contains("List of devices attached")) {
					startCount = true;
				} else if (startCount && !line.trim().isEmpty()
						&& !line.contains("unauthorized")
						&& !line.contains("offline")
						&& !line.contains("* daemon")) {
					String seg[] = line.split("\t");
					deviceName = seg[0];
					devices.add(deviceName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices;
	}
}
