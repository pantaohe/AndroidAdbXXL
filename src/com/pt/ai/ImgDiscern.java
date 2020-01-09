package com.pt.ai;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pt.config.Constant;
import com.pt.jo.DongWu;
import com.pt.jo.RGB;
import com.pt.po.Matrix;

public class ImgDiscern {
	
	static int TKR = 21, TKG = 39, TKB = 69,  
			XJR = 190, XJG = 158, XJB = 63,  
			QWR = 50, QWG = 155, QWB = 50,  
			HLR = 190, HLG = 45, HLB = 40,
			XXR = 160, XXG = 80, XXB = 22,  
			NNR = 55, NNG = 148, NNB = 210,  
			NNRO = 25, NNGO = 41, NNBO = 69,  NNRO2 = 70, NNGO2 = 120, NNBO2 = 165,  NNRR = 94, NNGR = 160, NNBR = 206;
	static double proportion = 0.0;
	public static void main(String[] args) {
		File file = new File("E:/eclipse-workspace/tools/img/xxl/level");
		File[] listFiles = file.listFiles();
		computeProportion(listFiles);
		System.err.println(proportion);
		if (proportion > 0.99) {
			System.out.println(toStringV());
		}
	}

	private static void computeProportion(File[] listFiles) {
		int countCorrect = 0;
		int index = 0;
		for (File file2 : listFiles) {
			if (file2.isFile()) {
				index ++;
				Matrix createMatrix = createMatrix(file2);
				int contrast = contrast(createMatrix, file2.getName());
				countCorrect += contrast;
			}
		}
		if (index != 0) {
			proportion = countCorrect * 1.0 / (index * 81);
		}
	}
	
	private static int contrast(Matrix createMatrix, String fileName) {
		String screen = getScreen(fileName);
		DongWu[][] matrixXY = createMatrix.getMatrixXY();
		int len = 0;
		int countCorrect = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				len = i * 29 + j * 3;
				String id = matrixXY[i][j].getId();
				String substring = screen.substring(len, len + 2);
				boolean flag = id.equals(substring);
				if (flag) {
					countCorrect ++;
				}else {
					System.out.println(i + "====" + j);
				}
				
			}
		}
		return countCorrect;
		
	}

	private static String getScreen(String fileName) {
		String screen = "";
		switch (fileName) {
		case "screen1.png":
			screen = ConstantImg.screen1;
			break;
		case "screen2.png":
			screen = ConstantImg.screen2;
			break;
		case "screen3.png":
			screen = ConstantImg.screen3;
			break;
		case "screen4.png":
			screen = ConstantImg.screen4;
			break;
		case "screen5.png":
			screen = ConstantImg.screen5;
			break;

		default:
			break;
		}
		return screen;
	}

	public static Matrix createMatrix(File imgFile) {
		Matrix matrix = new Matrix();
		if (matrix.toStringName().contains("未知")) {
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
		
		if(colourRrror(rgbIn, TKR, TKG, TKB)){
			dongWu.setId("TK");
			dongWu.setName("天空黑");
		}else if (colourRrror(rgbIn, 157, 113, 122)){
			dongWu.setId("WZ");
			dongWu.setName("王炸");
		}else if (colourRrror(rgbIn, 142, 55, 194, true)){
			dongWu.setId("LY");
			dongWu.setName("老鹰");
		}else if (colourRrror(rgbIn, XJR, XJG, XJB, true)){//rgbIn, 185, 155, 55
			dongWu.setId("XJ");
			dongWu.setName("小鸡");
		}else if (colourRrror(rgbIn, QWR, QWG, QWB, true)) {
			dongWu.setId("QW");
			dongWu.setName("青蛙");
		}else if (colourRrror(rgbIn, HLR, HLG, HLB, true)) {
			dongWu.setId("HL");
			dongWu.setName("狐狸");
		}else if (colourRrror(rgbIn, XXR, XXG, XXB, true)) {
			dongWu.setId("XX");
			dongWu.setName("小熊");
		}else if (colourRrror(rgbIn, NNR, NNG, NNB, true)) {//rgbIn, 55, 150, 210)
			if (colourRrror(rgbOut, NNRO, NNGO, NNBO) || colourRrror(rgbOut, NNRO2, NNGO2, NNBO2) || colourRrror(rgbOuterRing, NNRR, NNGR, NNBR)) {
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
	private static boolean colourRrror(RGB rgbIn, int par1, int par2, int par3, boolean flag) {
		int absR = Math.abs(rgbIn.getR() - par1);
		int absG = Math.abs(rgbIn.getG() - par2);
		int absB = Math.abs(rgbIn.getB() - par3);
		boolean b = absR < Constant.COLOUR_ERROR && 
				absG < Constant.COLOUR_ERROR && 
				absB < Constant.COLOUR_ERROR && 
				absR + absG + absB < Constant.COLOUR_ERROR * 2;
		
		int absG1 = absG + 15;
		int absB1 = absB + 20;
		boolean b2 = absR < Constant.COLOUR_ERROR && 
				absG1 < Constant.COLOUR_ERROR && 
				absB1 < Constant.COLOUR_ERROR && 
				absR + absG1 + absB1 < Constant.COLOUR_ERROR * 2;
		
		int absR2 = absR + 10;
		int absG2 = absG + 10;
		boolean b3 = absR2 < Constant.COLOUR_ERROR && 
				absG2 < Constant.COLOUR_ERROR && 
				absB < Constant.COLOUR_ERROR && 
				absR2 + absG2 + absB < Constant.COLOUR_ERROR * 2;
		return b || b2 || b3;
	}
	
	public static String toStringV() {
		return "ImgDiscern [TKR=" + TKR + ", TKG=" + TKG + ", TKB=" + TKB + ", XJR=" + XJR + ", XJG=" + XJG + ", XJB="
				+ XJB + ", QWR=" + QWR + ", QWG=" + QWG + ", QWB=" + QWB + ", HLR=" + HLR + ", HLG=" + HLG + ", HLB="
				+ HLB + ", XXR=" + XXR + ", XXG=" + XXG + ", XXB=" + XXB + ", NNR=" + NNR + ", NNG=" + NNG + ", NNB="
				+ NNB + ", NNRO=" + NNRO + ", NNGO=" + NNGO + ", NNBO=" + NNBO + ", NNRO2=" + NNRO2 + ", NNGO2=" + NNGO2
				+ ", NNBO2=" + NNBO2 + ", NNRR=" + NNRR + ", NNGR=" + NNGR + ", NNBR=" + NNBR + "]";
	}
}
