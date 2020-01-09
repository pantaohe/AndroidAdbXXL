package com.pt.utils.matrix;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pt.config.Constant;
import com.pt.jo.DongWu;
import com.pt.po.Matrix;
import com.pt.utils.cloner.ObjClonerSeiz;

public class MatrixMove {

	public static Map<int[], Matrix> matrixMove(Matrix matrix) {

		Map<int[], Matrix> matrixMap = new LinkedHashMap<int[], Matrix>();
		for (int i = 0; i < Constant.Y_COUNT; i++) {
			for (int j = 0; j < Constant.X_COUNT; j++) {
				exchange(i, j, matrix, matrixMap);
			}
		}

		//		for (Entry<int[], Matrix> entry : matrixMap.entrySet()) {
		//			System.out.println(entry.getKey()[0] + "行" + entry.getKey()[1] + "列" + entry.getKey()[2] + "方向");
		//		}
		return matrixMap;
	}

	public static void exchange(int i, int j, Matrix matrix, Map<int[], Matrix> matrixMap) {
		try {

			for (int k = 0; k < 2; k++) {
				Matrix cloneMatrixX = ObjClonerSeiz.CloneObj(matrix);

				DongWu dongWuX = cloneMatrixX.getDongWu(i, j);
				DongWu dongWuAddX = cloneMatrixX.getDongWu(i + 1 - k, j + k);
				if (k == 0) {
					dongWuX.setX(i + 1);
					dongWuAddX.setX(i);
				}else {
					dongWuX.setY(j + 1);
					dongWuAddX.setY(j);
				}
				cloneMatrixX.getMatrixXY()[i][j] = dongWuAddX;
				cloneMatrixX.getMatrixXY()[i + 1 - k][j + k] = dongWuX;

				
				
				String id = dongWuX.getId();
				String addId = dongWuAddX.getId();
				boolean bixu = !id.equals("TK") && !addId.equals("TK");
				boolean keyiWZ = id.equals("WZ") || addId.equals("WZ");
				boolean keyiJQ = dongWuX.isHasJQ() && dongWuAddX.isHasJQ();
				boolean keyi = keyiWZ || keyiJQ || eliminate(i, j, cloneMatrixX) || eliminate(i + 1 - k, j + k, cloneMatrixX);
				if (bixu && keyi){
					int score = 0;
					if (id.equals("WZ")) score += 15;
					else if (dongWuX.isHasJQ()) score += 10; 
					if (addId.equals("WZ")) score += 15;
					else if (dongWuAddX.isHasJQ()) score += 10;
					
					matrixMap.put(new int[]{i, j, k, score}, cloneMatrixX);
				}else {
					cloneMatrixX = null;
				}
			}
			
		} catch (Exception e) {
		}
	}

	/**
	 * 判断此点周围可否消除
	 * @param i
	 * @param j
	 * @param cloneMatrixX
	 * @return
	 */
	public static Boolean eliminate(int i, int j, Matrix cloneMatrixX) {
		DongWu dongWu = cloneMatrixX.getDongWu(i, j);
		if (i < 0 || i >= Constant.Y_COUNT || j < 0 || j >= Constant.X_COUNT || dongWu.getId().equals("TK"))
			return false;

		Boolean flag = false;
		DongWu dongWuNext1 = null;
		DongWu dongWuNext2 = null;
		for (int k = 0; k < 6; k++) {
			switch (k) {
			case 0:
				dongWuNext1 = cloneMatrixX.getDongWu(i - 1, j);
				dongWuNext2 = cloneMatrixX.getDongWu(i - 2, j);
				break;
			case 1:
				dongWuNext1 = cloneMatrixX.getDongWu(i - 1, j);
				dongWuNext2 = cloneMatrixX.getDongWu(i + 1, j);
				break;
			case 2:
				dongWuNext1 = cloneMatrixX.getDongWu(i + 1, j);
				dongWuNext2 = cloneMatrixX.getDongWu(i + 2, j);
				break;
			case 3:
				dongWuNext1 = cloneMatrixX.getDongWu(i, j - 1);
				dongWuNext2 = cloneMatrixX.getDongWu(i, j - 2);
				break;
			case 4:
				dongWuNext1 = cloneMatrixX.getDongWu(i, j - 1);
				dongWuNext2 = cloneMatrixX.getDongWu(i, j + 1);
				break;
			case 5:
				dongWuNext1 = cloneMatrixX.getDongWu(i, j + 1);
				dongWuNext2 = cloneMatrixX.getDongWu(i, j + 2);
				break;
			default:
				break;
			}

			try {
				flag = dongWu.getId().equals(dongWuNext1.getId()) && dongWu.getId().equals(dongWuNext2.getId());
				if (flag) break;
			} catch (Exception e) {
			}
		}
		return flag;
	}

}
