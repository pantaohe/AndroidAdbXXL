package com.pt.jo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.pt.config.Constant;

public class DongWu implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
//	private RGB[] rgbs = new RGB[5];
	
	private RGB rgbIn;
	private RGB rgbOut;
	
	private int x;
	private int y;
	private String pxXY;

	public DongWu() {
	}
	public DongWu(DongWu dongWu) {
		this.id = dongWu.getId();
		this.name = dongWu.getName();
//		this.rgbs = dongWu.getRgbs();
	}
	public DongWu(BufferedImage image, int i, int j) {
		this.pxXY = (i + Constant.SPEED/2) + " " + (j + Constant.SPEED/2) + " ";
		this.x = (j - Constant.Y_START)/Constant.SPEED;
		this.y = (i - Constant.X_START)/Constant.SPEED;
//		this.rgbs[0] = new RGB(image.getRGB(i + Constant.PX_MOVE, 						j + Constant.PX_MOVE));//左上角
//		this.rgbs[1] = new RGB(image.getRGB(i + Constant.SPEED - Constant.PX_MOVE - 1, 	j + Constant.PX_MOVE));//右上角
//		this.rgbs[2] = new RGB(image.getRGB(i + Constant.PX_MOVE, 						j + Constant.SPEED - Constant.PX_MOVE - 1));//左下角
//		this.rgbs[3] = new RGB(image.getRGB(i + Constant.SPEED - Constant.PX_MOVE - 1, 	j + Constant.SPEED - Constant.PX_MOVE - 1));//右下角
//		this.rgbs[4] = new RGB(image.getRGB(i + Constant.SPEED/2 - 1, 					j + Constant.SPEED/2 - 1));//中间
		
		getAvgRGB(image, i, j);
		
	}

	public void getAvgRGB(BufferedImage image, int i, int j) {
		int[] pxSumIn = new int[3];
		int[] pxSumOut = new int[3];
		int rgb = 0;
		int in = 0;
		int out = 0;
		for (int l = 0; l < Constant.SPEED; l += 2) {
			for (int k = 0; k < Constant.SPEED; k += 2) {
				rgb = image.getRGB(i + l, j + k);
				if (l + k < Constant.RGB_OUT_PX || Constant.SPEED - l + k < Constant.RGB_OUT_PX ||
						Constant.SPEED - k + l < Constant.RGB_OUT_PX || Constant.SPEED * 2 - k - l < Constant.RGB_OUT_PX) {
					out ++;
					pxSumOut[0] += rgb >> 16 & 0xff;
					pxSumOut[1] += rgb >> 8 & 0xff;
					pxSumOut[2] += rgb & 0xff;
				}else {
					in ++;
					pxSumIn[0] += rgb >> 16 & 0xff;
					pxSumIn[1] += rgb >> 8 & 0xff;
					pxSumIn[2] += rgb & 0xff;
				}
			}
		}
		this.rgbIn = new RGB(pxSumIn, in);
		this.rgbOut = new RGB(pxSumOut, out);
//		System.out.println("内层：R=" + pxSumIn[0]/in + ";G=" + pxSumIn[1]/in + ";B=" + pxSumIn[2]/in);
//		System.out.println("外层：R=" + pxSumOut[0]/out + ";G=" + pxSumOut[1]/out + ";B=" + pxSumOut[2]/out);
	}
	
	public Boolean moveUp() {
		try {
			int x = this.getX();
			if (x > 0) {
				this.x = x - 1;
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	public Boolean moveDown() {
		try {
			int x = this.getX();
			if (x < Constant.Y_COUNT - 1) {
				this.x = x + 1;
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	public Boolean moveRight() {
		try {
			int y = this.getY();
			if (y < Constant.X_COUNT - 1) {
				this.y = y + 1;
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	public Boolean moveLeft() {
		try {
			int y = this.getY();
			if (y > 0) {
				this.y = y - 1;
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	
//	public RGB[] getRgbs() {
//		return rgbs;
//	}
//
//	public void setRgbs(RGB[] rgbs) {
//		this.rgbs = rgbs;
//	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
//	public String toStringRGB() {
//		String rgbString = name + ":\r";
//		for (RGB rgb : rgbs) {
//			rgbString += rgb.getR() + "," + rgb.getG() + "," + rgb.getB() + "\r"; 
//		}
//		return rgbString;
//	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getPxXY() {
		return pxXY;
	}
	public void setPxXY(String pxXY) {
		this.pxXY = pxXY;
	}
	public RGB getRgbIn() {
		return rgbIn;
	}
	public void setRgbIn(RGB rgbIn) {
		this.rgbIn = rgbIn;
	}
	public RGB getRgbOut() {
		return rgbOut;
	}
	public void setRgbOut(RGB rgbOut) {
		this.rgbOut = rgbOut;
	}
}
