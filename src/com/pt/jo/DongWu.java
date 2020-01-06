package com.pt.jo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.pt.config.Constant;

public class DongWu implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	private RGB[] rgbs = new RGB[5];
	private int x;
	private int y;

	public DongWu() {
	}
	public DongWu(DongWu dongWu) {
		this.id = dongWu.getId();
		this.name = dongWu.getName();
		this.rgbs = dongWu.getRgbs();
	}
	public DongWu(BufferedImage image, int i, int j) {
		x = (j - Constant.Y_START)/Constant.SPEED;
		y = (i - Constant.X_START)/Constant.SPEED;
		rgbs[0] = new RGB(image.getRGB(i + Constant.PX_MOVE, 						j + Constant.PX_MOVE));//左上角
		rgbs[1] = new RGB(image.getRGB(i + Constant.SPEED - Constant.PX_MOVE - 1, 	j + Constant.PX_MOVE));//右上角
		rgbs[2] = new RGB(image.getRGB(i + Constant.PX_MOVE, 						j + Constant.SPEED - Constant.PX_MOVE - 1));//左下角
		rgbs[3] = new RGB(image.getRGB(i + Constant.SPEED - Constant.PX_MOVE - 1, 	j + Constant.SPEED - Constant.PX_MOVE - 1));//右下角
		rgbs[4] = new RGB(image.getRGB(i + Constant.SPEED/2 - 1, 					j + Constant.SPEED/2 - 1));//中间
	}

	/*public DongWu moveUp() {
		int x = this.getXy()[0];
		if (x >= 0) {
			DongWu dongWu = new DongWu(this);
			dongWu.xy = new int[] {x - 1, this.getXy()[1]};
			return dongWu;
		}
		return null;
	}
	public DongWu moveDown() {
		int x = this.getXy()[0];
		if (x <= Constant.X_COUNT) {
			DongWu dongWu = new DongWu(this);
			dongWu.xy = new int[] {x + 1, this.getXy()[1]};
			return dongWu;
		}
		return null;
	}
	public DongWu moveRight() {
		int y = this.getXy()[1];
		if (y >= 0) {
			DongWu dongWu = new DongWu(this);
			dongWu.xy = new int[] {this.getXy()[0], y + 1};
			return dongWu;
		}
		return null;
	}
	public DongWu moveLeft() {
		int y = this.getXy()[1];
		if (y >= Constant.Y_COUNT) {
			DongWu dongWu = new DongWu(this);
			dongWu.xy = new int[] {this.getXy()[0], y - 1};
			return dongWu;
		}
		return null;
	}*/
	
	
	public RGB[] getRgbs() {
		return rgbs;
	}

	public void setRgbs(RGB[] rgbs) {
		this.rgbs = rgbs;
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
	
	public String toStringRGB() {
		String rgbString = name + ":\r";
		for (RGB rgb : rgbs) {
			rgbString += rgb.getR() + "," + rgb.getG() + "," + rgb.getB() + "\r"; 
		}
		return rgbString;
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
}
