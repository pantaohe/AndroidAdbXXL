package com.pt.po;

import java.io.Serializable;

import com.pt.config.Constant;
import com.pt.jo.DongWu;

public class Matrix implements Serializable{
	private static final long serialVersionUID = 1L;
//	private DongWu[][] matrixXY = new DongWu[Constant.Y_COUNT][Constant.X_COUNT];
	private DongWu[][] matrixXY;

	public DongWu getDongWu(int x, int y){
		try {
			return  matrixXY[x][y];
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean moveDown(int x, int y) {
		try {
			DongWu dongWu = this.getDongWu(x, y);
			if (x < Constant.Y_COUNT - 1 && dongWu.moveDown()) {
				this.getMatrixXY()[x + 1][y] = dongWu;
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	
	public DongWu[][] getMatrixXY() {
		return matrixXY;
	}

	public void setMatrixXY(DongWu[][] matrixXY) {
		this.matrixXY = matrixXY;
	}

	public String toStringName() {
		String matrixToString = "矩阵为\r";
		for (DongWu[] dongWus : matrixXY) {
			for (DongWu dongWu : dongWus) {
				try {
					matrixToString += dongWu.getName() + "\t";
				} catch (Exception e) {
					matrixToString += "未知\t";
//					e.printStackTrace();
				}
			}
			matrixToString += "\r";
		}
		return matrixToString;
	}
	public String toStringId() {
		String matrixToString = "矩阵为\r";
		for (DongWu[] dongWus : matrixXY) {
			for (DongWu dongWu : dongWus) {
				try {
					matrixToString += dongWu.getId() + "\t";
				} catch (Exception e) {
					matrixToString += "未知\t";
				}
			}
			matrixToString += "\r";
		}
		return matrixToString;
	}
	
	public String toStringRGB() {
		String matrixToString = "矩阵为\r";
		for (DongWu[] dongWus : matrixXY) {
			String hx = "";
			String h0 = "";
			String h1 = "";
			String h2 = "";
			for (DongWu dongWu : dongWus) {
				hx += dongWu.getId() + "\t\t\t\t";
				h0 += dongWu.getRgbIn().toString() + "\t";
				h1 += dongWu.getRgbOut().toString() + "\t";
				h2 += dongWu.getRgbOuterRing().toString() + "\t";
			}
			matrixToString += hx + "\r" + h0 + "\r" + h1 + "\r" + h2 + "\r\r";
		}
		return matrixToString;
	}
	
}
