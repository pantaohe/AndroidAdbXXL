package com.pt;

import com.pt.jo.DongWu;
import com.pt.po.Matrix;
import com.pt.utils.ConnAndroid;
import com.pt.utils.MatrixUtils;

public class Main {

	public static void main(String[] args) {
		ConnAndroid.connAnd();
		ConnAndroid.dpi();
		//建立xxl矩阵
		Matrix matrix = MatrixUtils.createMatrix();
		
		matrixMove(matrix);
		
	}

	private static void matrixMove(Matrix matrix) {
		DongWu[][] matrixXY = matrix.getMatrixXY();
		
		for (int i = 0; i < matrixXY.length; i++) {
			for (int j = 0; j < matrixXY.length; j++) {
				
			}
		}
		
	}

	

	

	
	
	
}
