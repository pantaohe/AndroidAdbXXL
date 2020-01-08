package com.pt.utils.matrix;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pt.config.Constant;
import com.pt.jo.DongWu;
import com.pt.jo.RGB;
import com.pt.po.Matrix;
import com.pt.utils.Screen;

public class MatrixCreate {

	public static Matrix createMatrix() {
		Matrix matrix = new Matrix();
		if (matrix.toStringName().contains("未知")) {
			File imgFile = null;
			if (Constant.DEBUG){
				imgFile = new File("resources/img/temp/screen1.png");
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
		RGB rgbIn = dongWu.getRgbIn();
		RGB rgbOut = dongWu.getRgbOut();
		RGB rgbOuterRing = dongWu.getRgbOuterRing();
		
		if (dongWu.getX() == 5 && dongWu.getY() == 0) {
			System.out.println(12);
		}
		
		if(colourRrror(rgbIn, 21, 39, 69)){
			dongWu.setId("TK");
			dongWu.setName("天空黑");
		}else if (colourRrror(rgbIn, 195, 160, 60)){//rgbIn, 185, 155, 55
			dongWu.setId("XJ");
			dongWu.setName("小鸡");
		}else if (colourRrror(rgbIn, 50, 155, 50)) {
			dongWu.setId("QW");
			dongWu.setName("青蛙");
		}else if (colourRrror(rgbIn, 190, 50, 45)) {
			dongWu.setId("HL");
			dongWu.setName("狐狸");
		}else if (colourRrror(rgbIn, 160, 80, 22)) {
			dongWu.setId("XX");
			dongWu.setName("小熊");
		}else if (colourRrror(rgbIn, 55, 150, 210)) {//rgbIn, 55, 150, 210)
			if (colourRrror(rgbOut, 25, 41, 69) || colourRrror(rgbOut, 70, 120, 165) || colourRrror(rgbOuterRing, 94, 160, 206)) {
				dongWu.setId("NN");
				dongWu.setName("奶牛");
			}else {
				dongWu.setId("TK");
				dongWu.setName("天空白");
			}
		}else {
			dongWu.setId("TK");
			dongWu.setName("天空白");
		}
		
		if ("TK".equals(dongWu.getId())){
			return;
		}else if (colourRrror(rgbOut, 40, 40, 60)){//背面不带冰块
			dongWu.setHasIce(false);
		}else if (colourRrror(rgbOut, 90, 150, 200)){
			dongWu.setHasIce(true);
		}

		
		//内层[r=34, g=145, b=204]
//		外层[r=27, g=109, b=162]
		/*if (colourRrror(rgbOut, 40, 150, 200) || colourRrror(rgbIn, 21, 38, 68)) {
			dongWu.setId("TK");
			dongWu.setName("天空");
		}else if (colourRrror(rgbOut, 30, 39, 64)){//背面不带冰块
			dongWu.setHasIce(false);
			if (colourRrror(rgbIn, 148, 119, 55)){
				dongWu.setId("XJ");
				dongWu.setName("小鸡");
			}else if (colourRrror(rgbIn, 43, 121, 174)) {
				dongWu.setId("NN");
				dongWu.setName("奶牛");
			}else if (colourRrror(rgbIn, 40, 125, 52)) {
				dongWu.setId("QW");
				dongWu.setName("青蛙");
			}else if (colourRrror(rgbIn, 147, 45, 45)) {
				dongWu.setId("HL");
				dongWu.setName("狐狸");
			}else {
				dongWu.setId("WZ");
				dongWu.setName("未知");
				System.out.println("位置：" + dongWu.getX() + "行," + dongWu.getY() + "列");
				System.err.println("内层" + rgbIn);
				System.err.println("外层" + rgbOut);
			}
		}else if (colourRrror(rgbOut, 71, 123, 162)){//背景带冰块
			dongWu.setHasIce(true);
			if (colourRrror(rgbIn, 148, 119, 55)){
				dongWu.setId("XJ");
				dongWu.setName("小鸡");
			}else if (colourRrror(rgbIn, 43, 121, 174)) {
				dongWu.setId("NN");
				dongWu.setName("奶牛");
			}else if (colourRrror(rgbIn, 40, 125, 52)) {
				dongWu.setId("QW");
				dongWu.setName("青蛙");
			}else if (colourRrror(rgbIn, 147, 45, 45)) {
				dongWu.setId("HL");
				dongWu.setName("狐狸");
			}else {
				dongWu.setId("WZ");
				dongWu.setName("未知");
				System.out.println("位置：" + dongWu.getX() + "行," + dongWu.getY() + "列");
				System.err.println("内层" + rgbIn);
				System.err.println("外层" + rgbOut);
			}
		}else {
			dongWu.setId("TK");
			dongWu.setName("天空");
		}*/
	}

	private static boolean colourRrror(RGB rgbIn, int par1, int par2, int par3) {
		int absR = Math.abs(rgbIn.getR() - par1);
		int absG = Math.abs(rgbIn.getG() - par2);
		int absB = Math.abs(rgbIn.getB() - par3);
		return absR < Constant.COLOUR_ERROR && 
				absG < Constant.COLOUR_ERROR && 
				absB < Constant.COLOUR_ERROR && 
				absR + absG + absB < Constant.COLOUR_ERROR * 2;
	}

	
}
