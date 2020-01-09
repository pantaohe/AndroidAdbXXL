package com.pt.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.pt.config.Constant;
import com.pt.jo.RGB;

public class CreateDongWuColour {
	static String[] row = new String[9];
	static BufferedImage image = null;
	static OutputStream fileTextOut = null;
	public static void main(String[] args) throws IOException {
		File file = new File("E:/eclipse-workspace/tools/img/xxl/level/screen.png");
		File fileText = new File("resources/text/第六关圈炸1111111.txt");
		row[0] = "";
		row[1] = "";
		row[2] = "";
		row[3] = "";
		row[4] = "";
		row[5] = "";
		row[6] = "";
		row[7] = "";
		row[8] = "";
		
		
		
		image = ImageIO.read(file);
		fileTextOut = new FileOutputStream(fileText);
		
		String s = "		row[0] = \"\";\r\n" + 
				"		row[1] = \"\";\r\n" + 
				"		row[2] = \"\";\r\n" + 
				"		row[3] = \"\";\r\n" + 
				"		row[4] = \"\";\r\n" + 
				"		row[5] = \"\";\r\n" + 
				"		row[6] = \"01\";\r\n" + 
				"		row[7] = \"\";\r\n" + 
				"		row[8] = \"\"";
		fileTextOut.write(s.getBytes());
		action();
		fileTextOut.flush();
		fileTextOut.close();
		
	}
	private static void action() throws IOException {
		List<RGB[]> list = new ArrayList<RGB[]>();
		
		for (int i = 0; i < row.length; i++) {
			String rowx = row[i];
			if (!"".equals(rowx)) {
				for (int j = 0; j < rowx.length(); j++) {
					int y = Integer.parseInt(rowx.charAt(j) + "");
					RGB[] printfImg = printfImg(image, i, y);
					list.add(printfImg);
				}
			}
		}
		maxRGB(list);
		
	}

	
	private static RGB[] printfImg(BufferedImage image, int i1, int j1) throws IOException {
		int i = j1 * 120;
		int j = 545 + i1 * 120;
		String s = i1 + "行" + j1 + "列\r";
		fileTextOut.write(s.getBytes());
		
		int[] pxSumIn = new int[3];
		int[] pxSumOut = new int[3];
		int[] pxSumOuterRing = new int[3];
		int rgb = 0;
		int in = 0;
		int out = 0;
		int outer = 0;
		long time = System.currentTimeMillis();
		for (int l = 0; l < Constant.SPEED; l += 1) {
			for (int k = 0; k < Constant.SPEED; k += 1) {
				rgb = image.getRGB(i + l, j + k);
				if (equal(l, k, Constant.OUTER_RING)) {
					outer++;
					pxSumOuterRing[0] += rgb >> 16 & 0xff;
					pxSumOuterRing[1] += rgb >> 8 & 0xff;
					pxSumOuterRing[2] += rgb & 0xff;
				}else if(getOutScope(l, k, Constant.PX_MOVE, Constant.RGB_OUT_PX)) {
					out ++;
					pxSumOut[0] += rgb >> 16 & 0xff;
					pxSumOut[1] += rgb >> 8 & 0xff;
					pxSumOut[2] += rgb & 0xff;
				}else if (getInScope(l, k, Constant.RGB_IN_PX)){
					in ++;
					pxSumIn[0] += rgb >> 16 & 0xff;
					pxSumIn[1] += rgb >> 8 & 0xff;
					pxSumIn[2] += rgb & 0xff;
				}
			}
		}
		long l = System.currentTimeMillis() - time;
		String s1 = "内层：R=" + pxSumIn[0]/in + ";G=" + pxSumIn[1]/in + ";B=" + pxSumIn[2]/in + "\r" + 
				"外层：R=" + pxSumOut[0]/out + ";G=" + pxSumOut[1]/out + ";B=" + pxSumOut[2]/out + "\r" + 
				"外圈：R=" + pxSumOuterRing[0]/outer + ";G=" + pxSumOuterRing[1]/outer + ";B=" + pxSumOuterRing[2]/outer + "\r" +
				"out=" + out + "in=" + in + "outer=" + outer + "\r" + l + "ms\r";
		fileTextOut.write(s1.getBytes());
		
		RGB rgbs1 = new RGB(pxSumIn, in);
		RGB rgbs2 = new RGB(pxSumOut, out);
		RGB rgbs3 = new RGB(pxSumOuterRing, outer);
		return new RGB[] {rgbs1, rgbs2, rgbs3};
		
	}
	private static boolean getInScope(int l, int k, int rgbOutPx) {
		return Math.abs(l - Constant.SPEED/2) < rgbOutPx && Math.abs(k - Constant.SPEED/2) < rgbOutPx;
	}
	private static boolean equal(int l, int k, int par) {
		return l == par || k == par || (Constant.SPEED - l) == par || (Constant.SPEED - k) == par;
	}
	//主要算左右两边，上下两边因为不同关卡的起点像素位置不同，所以上下两边多去掉了一个pxMove
	private static boolean getOutScope(int l, int k, int pxMove, int rgbOutPx) {
		return (l + k < rgbOutPx || Constant.SPEED - l + k < rgbOutPx || 
					Constant.SPEED - k + l < rgbOutPx || Constant.SPEED * 2 - k - l < rgbOutPx) && 
				(l > pxMove && k > pxMove * 2 && (Constant.SPEED - l) > pxMove && (Constant.SPEED - k) > pxMove * 2);
	}
	
	private static void maxRGB(List<RGB[]> list) throws IOException {
		int maxR1 = 0, minR1 = Integer.MAX_VALUE, maxG1 = 0, minG1 = Integer.MAX_VALUE, maxB1 = 0, minB1 = Integer.MAX_VALUE; 
		int maxR2 = 0, minR2 = Integer.MAX_VALUE, maxG2 = 0, minG2 = Integer.MAX_VALUE, maxB2 = 0, minB2 = Integer.MAX_VALUE; 
		int maxR3 = 0, minR3 = Integer.MAX_VALUE, maxG3 = 0, minG3 = Integer.MAX_VALUE, maxB3 = 0, minB3 = Integer.MAX_VALUE; 
		
		for (RGB[] rgbs : list) {
			int r1 = rgbs[0].getR(); if (r1 > maxR1) maxR1 = r1; if (r1 < minR1) minR1 = r1;
			int r2 = rgbs[1].getR(); if (r2 > maxR2) maxR2 = r2; if (r2 < minR2) minR2 = r2;
			int r3 = rgbs[2].getR(); if (r3 > maxR3) maxR3 = r3; if (r3 < minR3) minR3 = r3;
			
			int g1 = rgbs[0].getG(); if (g1 > maxG1) maxG1 = g1; if (g1 < minG1) minG1 = g1;
			int g2 = rgbs[1].getG(); if (g2 > maxG2) maxG2 = g2; if (g2 < minG2) minG2 = g2;
			int g3 = rgbs[2].getG(); if (g3 > maxG3) maxG3 = g3; if (g3 < minG3) minG3 = g3;
			
			int b1 = rgbs[0].getB(); if (b1 > maxB1) maxB1 = b1; if (b1 < minB1) minB1 = b1;
			int b2 = rgbs[1].getB(); if (b2 > maxB2) maxB2 = b2; if (b2 < minB2) minB2 = b2;
			int b3 = rgbs[2].getB(); if (b3 > maxB3) maxB3 = b3; if (b3 < minB3) minB3 = b3;
		}
		String s = "\r最大值与最小值\r" + 
				"内层：" + maxR1 + "," + minR1 + "\t"  + maxG1 + "," + minG1 + "\t"  + maxB1 + "," + minB1 + "\r" + 
				"外层：" + maxR2 + "," + minR2 + "\t"  + maxG2 + "," + minG2 + "\t"  + maxB2 + "," + minB2 + "\r" + 
				"外圈：" + maxR3 + "," + minR3 + "\t"  + maxG3 + "," + minG3 + "\t"  + maxB3 + "," + minB3;
		fileTextOut.write(s.getBytes());
	}
}
