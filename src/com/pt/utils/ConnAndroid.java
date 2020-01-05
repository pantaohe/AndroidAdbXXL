package com.pt.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConnAndroid {
	
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
