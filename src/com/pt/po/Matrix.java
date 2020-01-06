package com.pt.po;

import java.io.Serializable;

import com.pt.config.Constant;
import com.pt.jo.DongWu;

public class Matrix implements Serializable{
	private static final long serialVersionUID = 1L;
	private DongWu[][] matrixXY = new DongWu[Constant.Y_COUNT][Constant.X_COUNT];

	public DongWu getDongWu(int x, int y){
		try {
			return  matrixXY[x][y];
		} catch (Exception e) {
			return null;
		}
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
	
	public String toStringRGB() {
		String matrixToString = "矩阵为\r";
		for (DongWu[] dongWus : matrixXY) {
			String hx = "";
			String h0 = "";
			String h1 = "";
			String h2 = "";
			String h3 = "";
			String h4 = "";
			for (DongWu dongWu : dongWus) {
				hx += dongWu.getId() + "\t\t\t\t";
				h0 += dongWu.getRgbs()[0].toString() + "\t";
				h1 += dongWu.getRgbs()[1].toString() + "\t";
				h2 += dongWu.getRgbs()[2].toString() + "\t";
				h3 += dongWu.getRgbs()[3].toString() + "\t";
				h4 += dongWu.getRgbs()[4].toString() + "\t";
			}
			matrixToString += hx + "\r" + h0 + "\r" + h1 + "\r" + h2 + "\r" + h3 + "\r" + h4 + "\r\r";
		}
		return matrixToString;
	}
	
}
