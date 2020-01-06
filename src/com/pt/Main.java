package com.pt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pt.config.Constant;
import com.pt.jo.DongWu;
import com.pt.po.Matrix;
import com.pt.utils.MatrixUtils;
import com.pt.utils.ObjClonerSeiz;

public class Main {

	public static void main(String[] args) {
//		ConnAndroid.connAnd();
//		ConnAndroid.dpi();
		//建立xxl矩阵
		Matrix matrix = MatrixUtils.createMatrix();
		
		matrixMove(matrix);
		
	}

	private static void matrixMove(Matrix matrix) {
		
		Map<int[], Matrix> matrixMap = new HashMap<int[], Matrix>();
		for (int i = 0; i < Constant.Y_COUNT; i++) {
			for (int j = 0; j < Constant.X_COUNT; j++) {
				exchange(i, j, matrix, matrixMap);
				break;
			}
			break;
		}
		
	}

	private static void exchange(int i, int j, Matrix matrix, Map<int[], Matrix> matrixMap) {
		try {
			
			Matrix cloneMatrixX = ObjClonerSeiz.CloneObj(matrix);
			Matrix cloneMatrixY = ObjClonerSeiz.CloneObj(matrix);
			
			DongWu dongWuX = cloneMatrixX.getDongWu(i, j);
			dongWuX.setX(i + 1);
			DongWu dongWuAddX = cloneMatrixX.getDongWu(i + 1, j);
			dongWuAddX.setX(i);
			cloneMatrixX.getMatrixXY()[i][j] = dongWuAddX;
			cloneMatrixX.getMatrixXY()[i + 1][j] = dongWuX;
			
			Boolean flag = eliminate(i, j, cloneMatrixX);
			if (flag){
				matrixMap.put(new int[]{i, j, 0}, cloneMatrixX);
			}
			
			
			
			DongWu dongWuY = cloneMatrixY.getDongWu(i, j);
			dongWuY.setY(j + 1);
			DongWu dongWuAddY = cloneMatrixY.getDongWu(i, j + 1);
			dongWuAddY.setY(j);
			cloneMatrixY.getMatrixXY()[i][j] = dongWuAddY;
			cloneMatrixY.getMatrixXY()[i][j + 1] = dongWuY;
			
			matrixMap.put(new int[]{i, j, 1}, cloneMatrixY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断此点周围可否消除
	 * @param i
	 * @param j
	 * @param cloneMatrixX
	 * @return
	 */
	private static Boolean eliminate(int i, int j, Matrix cloneMatrixX) {
		// TODO Auto-generated method stub
		return null;
	}


	

	

	
	
	
}
