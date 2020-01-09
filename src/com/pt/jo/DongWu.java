package com.pt.jo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.pt.config.Constant;

public class DongWu implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private boolean hasIce = false;
	private boolean hasJQ = false;
	
	private RGB rgbIn;
	private RGB rgbOut;
	private RGB rgbOuterRing;
	
	private int x;
	private int y;
	private String pxXY;

	public DongWu() {
	}
	public DongWu(DongWu dongWu) {
		this.id = dongWu.getId();
		this.name = dongWu.getName();
	}
	public DongWu(BufferedImage image, int i, int j) {
		this.pxXY = (i + Constant.SPEED/2) + " " + (j + Constant.SPEED/2) + " ";
		this.x = (j - Constant.Y_START)/Constant.SPEED;
		this.y = (i - Constant.X_START)/Constant.SPEED;
		
		setAvgRGB(image, i, j);
	}

	public void setAvgRGB(BufferedImage image, int i, int j) {
		int[] pxSumIn = new int[3];
		int[] pxSumOut = new int[3];
		int[] pxSumOuterRing = new int[3];//一个像素点的外圈
		int rgb = 0;
		int in = 0;
		int out = 0;
		int outer = 0;
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
		this.rgbIn = new RGB(pxSumIn, in);
		this.rgbOut = new RGB(pxSumOut, out);
		this.rgbOuterRing = new RGB(pxSumOuterRing, outer);
//		System.out.println("内层：R=" + pxSumIn[0]/in + ";G=" + pxSumIn[1]/in + ";B=" + pxSumIn[2]/in);范围
//		System.out.println("外层：R=" + pxSumOut[0]/out + ";G=" + pxSumOut[1]/out + ";B=" + pxSumOut[2]/out);
	}
	private boolean getInScope(int l, int k, int rgbOutPx) {
		return Math.abs(l - Constant.SPEED/2) < rgbOutPx && Math.abs(k - Constant.SPEED/2) < rgbOutPx;
	}
	private boolean equal(int l, int k, int par) {
		return l == par || k == par || (Constant.SPEED - l) == par || (Constant.SPEED - k) == par;
	}
	//主要算左右两边，上下两边因为不同关卡的起点像素位置不同，所以上下两边多去掉了一个pxMove
	private boolean getOutScope(int l, int k, int pxMove, int rgbOutPx) {
		return (l + k < rgbOutPx || Constant.SPEED - l + k < rgbOutPx || 
					Constant.SPEED - k + l < rgbOutPx || Constant.SPEED * 2 - k - l < rgbOutPx) && 
				(l > pxMove && k > pxMove * 2 && (Constant.SPEED - l) > pxMove && (Constant.SPEED - k) > pxMove * 2);
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
	public boolean isHasIce() {
		return hasIce;
	}
	public void setHasIce(boolean hasIce) {
		this.hasIce = hasIce;
	}
	public RGB getRgbOuterRing() {
		return rgbOuterRing;
	}
	public void setRgbOuterRing(RGB rgbOuterRing) {
		this.rgbOuterRing = rgbOuterRing;
	}
	public boolean isHasJQ() {
		return hasJQ;
	}
	public void setHasJQ(boolean hasJQ) {
		this.hasJQ = hasJQ;
	}
}
