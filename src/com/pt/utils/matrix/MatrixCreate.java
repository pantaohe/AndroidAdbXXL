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
		File imgFile = null;
		if (Constant.DEBUG){
			imgFile = new File(Constant.DEBUG_IMG_PATH);
		}else{
			imgFile = Screen.getImgFile();
		}
		try {
			BufferedImage image = ImageIO.read(imgFile);
			
			setXYStartEnd(image);
			matrix.setMatrixXY(new DongWu[Constant.Y_COUNT][Constant.X_COUNT]);
			
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
		return matrix;
	}
	
	private static void setXYStartEnd(BufferedImage image) {
		boolean flagStart = true;
		boolean flagEnd = false;
		int start = 0;
		int end = 0;
		RGB rgb = null;
		int index = 0;//当黑色的行数达到一定次数才重新计算end值
		for (int i = Constant.HEIGHT_PX / 5; i < Constant.HEIGHT_PX * 17 / 20; i++) {
			for (int j = 0; j < Constant.WIDTH_PX; j++) {
				rgb = new RGB(image.getRGB(j, i));
				boolean flagRGB = rgb.getR() < 40 && rgb.getG() < 70 && rgb.getB() < 120;
				if (flagStart && flagRGB) {
					start = i;
					flagStart = false; flagEnd = true;
					break;
				}
				if (flagEnd && !flagRGB) {
					end = i;
					flagEnd = false;
					break;
				}
				if (!flagStart && !flagEnd && flagRGB) {
					index++;
					if (index > 12){
						index = 0;
						flagEnd = true;
					}
				}
			}
		}
		
		int indexPX = end - start + Constant.SPEED/2;
		Constant.Y_COUNT = indexPX / Constant.SPEED;
		int error = indexPX % Constant.SPEED - Constant.SPEED/2;
		Constant.Y_START = start - error/2;
		Constant.Y_END = Constant.Y_START + Constant.Y_COUNT * Constant.SPEED;
		
		Constant.X_END = Constant.X_COUNT * Constant.SPEED;
	}

	public static void caseDongWu(DongWu dongWu) {
		RGB rgbIn = dongWu.getRgbIn();
		RGB rgbOut = dongWu.getRgbOut();
		RGB rgbOuterRing = dongWu.getRgbOuterRing();
		
//		if (dongWu.getX() == 5 && dongWu.getY() == 0) {
//			System.out.println(12);
//		}
		
		if(colourRrror(rgbIn, 21, 39, 69)){
			dongWu.setId("TK");
			dongWu.setName("天空黑");
		}else if (colourRrror(rgbIn, 157, 113, 122)){
			dongWu.setId("WZ");
			dongWu.setName("王炸");
		}else if (colourRrror(rgbIn, 142, 55, 194, dongWu)){
			dongWu.setId("LY");
			dongWu.setName("老鹰");
		}else if (colourRrror(rgbIn, 190, 158, 63, dongWu)){//rgbIn, 185, 155, 55
			dongWu.setId("XJ");
			dongWu.setName("小鸡");
		}else if (colourRrror(rgbIn, 50, 155, 50, dongWu)) {
			dongWu.setId("QW");
			dongWu.setName("青蛙");
		}else if (colourRrror(rgbIn, 190, 45, 40, dongWu)) {
			dongWu.setId("HL");
			dongWu.setName("狐狸");
		}else if (colourRrror(rgbIn, 160, 80, 22, dongWu)) {
			dongWu.setId("XX");
			dongWu.setName("小熊");
		}else if (colourRrror(rgbIn, 55, 148, 210, dongWu)) {//rgbIn, 55, 150, 210)
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

	}

	private static boolean colourRrror(RGB rgbIn, int par1, int par2, int par3) {
		int absR = Math.abs(rgbIn.getR() - par1);
		int absG = Math.abs(rgbIn.getG() - par2);
		int absB = Math.abs(rgbIn.getB() - par3);
		boolean b = absR < Constant.COLOUR_ERROR && 
				absG < Constant.COLOUR_ERROR && 
				absB < Constant.COLOUR_ERROR && 
				absR + absG + absB < Constant.COLOUR_ERROR * 2;
		return b;
	}
	private static boolean colourRrror(RGB rgbIn, int par1, int par2, int par3, DongWu dongWu) {
		boolean b = colourRrror(rgbIn, par1, par2, par3);
		
		if (!b) {
			int absR = Math.abs(rgbIn.getR() - par1);
			int absG = Math.abs(rgbIn.getG() - par2 - 15);
			int absB = Math.abs(rgbIn.getB() - par3 -20);
			boolean b2 = absR < Constant.COLOUR_ERROR && 
					absG < Constant.COLOUR_ERROR && 
					absB < Constant.COLOUR_ERROR && 
					absR + absG + absB < Constant.COLOUR_ERROR * 2;
			
			absR = Math.abs(rgbIn.getR() - par1 - 10);
			absG = Math.abs(rgbIn.getG() - par2 - 10);
			absB = Math.abs(rgbIn.getB() - par3);
			boolean b3 = absR < Constant.COLOUR_ERROR && 
					absG < Constant.COLOUR_ERROR && 
					absB < Constant.COLOUR_ERROR && 
					absR + absG + absB < Constant.COLOUR_ERROR * 2;
			if (b2 || b3)
				dongWu.setHasJQ(true);
			return b || b2 || b3;
		}
		return b;
	}

	
}
