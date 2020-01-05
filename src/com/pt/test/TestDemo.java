package com.pt.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestDemo {

	public static void main(String[] args) {
		try {
			File file = new File("resources/img/temp/xxl.jpg");
			BufferedImage image = ImageIO.read(file);
			
			int height = image.getHeight();
			int width = image.getWidth();
			System.out.println(width + "x" + height);
			
			
//			for (int i = 0; i < height; i++) {
//				for (int j = 0; j < width; j++) {
//					System.out.print(image.getRGB(j, i) + "\t");
//				}
//				System.out.println();
//			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
