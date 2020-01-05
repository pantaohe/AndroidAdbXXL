package com.pt;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.pt.utils.ConnAndroid;
import com.pt.utils.Screen;

public class Main {

	public static void main(String[] args) {
		ConnAndroid.connAnd();
		int[] dpi = dpi();
	}

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

	
	
	
}
