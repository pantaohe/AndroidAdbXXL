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
				imgFile = new File("resources/img/temp/screen2.png");
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
		
		if (colourRrror(rgbOut, 30, 39, 64)){//背面不带冰块
			dongWu.setHasIce(false);
		}else if (colourRrror(rgbOut, 71, 123, 162)){
			dongWu.setHasIce(true);
		}else {
			dongWu.setId("TK");
			dongWu.setName("天空白");
		}
		
		if (dongWu.getName() != null && !"".equals(dongWu.getName())){
			System.out.println(dongWu.getName());
		}else if(colourRrror(rgbIn, 21, 38, 68)){
			dongWu.setId("TK");
			dongWu.setName("天空黑");
		}else if (colourRrror(rgbIn, 148, 119, 55)){
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
		return Math.abs(rgbIn.getR() - par1) < Constant.COLOUR_ERROR && 
				Math.abs(rgbIn.getG() - par2) < Constant.COLOUR_ERROR && 
				Math.abs(rgbIn.getB() - par3) < Constant.COLOUR_ERROR;
	}

	
}
