package com.pt.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pt.config.Constant;
import com.pt.jo.DongWu;
import com.pt.jo.RGB;
import com.pt.po.Matrix;

public class MatrixUtils {

	public static Matrix createMatrix() {
		Matrix matrix = new Matrix();
		while (matrix.toStringName().contains("未知")) {
			File imgFile = null;
			if (Constant.DEBUG){
				imgFile = new File("resources/img/temp/screen.png");
			}else{
				imgFile = Screen.getImgFile();
			}
			try {
				BufferedImage image = ImageIO.read(imgFile);
				
				for (int i = Constant.X_START; i < Constant.X_END; i = i + Constant.SPEED) {
					for (int j = Constant.Y_START; j < Constant.Y_END; j = j + Constant.SPEED) {

						DongWu dongWu = new DongWu(image, i, j);

						caseDongWu(dongWu);

						matrix.getMatrixXY()[dongWu.getX()][dongWu.getY()] = dongWu;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return matrix;
		
	}

	public static void caseDongWu(DongWu dongWu) {
		RGB[] rgbs = dongWu.getRgbs();
		
		int gSum = 0;
		int bSum = 0;
		for (RGB rgb : rgbs) {
			gSum = gSum + rgb.getG();
			bSum = bSum + rgb.getB();
		}
		
		RGB centre = rgbs[4];
		if (gSum + bSum > 1000) {
			dongWu.setId("TK");
			dongWu.setName("天空");
		}else if (centre.getR() + centre.getG() > 400){
			dongWu.setId("XJ");
			dongWu.setName("小鸡");
		}else if (centre.getG() + centre.getB() > 350) {
			dongWu.setId("NN");
			dongWu.setName("奶牛");
		}else if (centre.getG() > 150 && (centre.getR() + centre.getB()) < 200) {
			dongWu.setId("QW");
			dongWu.setName("青蛙");
		}else if (centre.getR() > 150 && (centre.getG() + centre.getB()) < 200) {
			dongWu.setId("HL");
			dongWu.setName("狐狸");
		}else {
			dongWu.setId("WZ");
			dongWu.setName("未知");
			System.out.println("位置：" + dongWu.getX() + "行," + dongWu.getY() + "列");
			for (RGB rgb : rgbs) {
				System.err.println(rgb);
			}
		}
	}
	
}
