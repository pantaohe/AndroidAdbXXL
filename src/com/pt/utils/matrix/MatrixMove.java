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

			Matrix cloneMatrixX = ObjClonerSeiz.CloneObj(matrix);
			Matrix cloneMatrixY = ObjClonerSeiz.CloneObj(matrix);

			DongWu dongWuX = cloneMatrixX.getDongWu(i, j);
			dongWuX.setX(i + 1);
			DongWu dongWuAddX = cloneMatrixX.getDongWu(i + 1, j);
			dongWuAddX.setX(i);
			cloneMatrixX.getMatrixXY()[i][j] = dongWuAddX;
			cloneMatrixX.getMatrixXY()[i + 1][j] = dongWuX;

			String xId = dongWuX.getId();
			String xaddId = dongWuAddX.getId();
			boolean xbixu = !xId.equals("TK") && !xaddId.equals("TK");
			boolean xkeyi = xId.equals("WZ") || xaddId.equals("WZ");
			xkeyi = xkeyi || (dongWuX.isHasJQ() && dongWuAddX.isHasJQ());
			xkeyi = xkeyi || eliminate(i, j, cloneMatrixX) || eliminate(i + 1, j, cloneMatrixX);
			if (xbixu && xkeyi){
				matrixMap.put(new int[]{i, j, 0}, cloneMatrixX);
			}else {
				cloneMatrixX = null;
			}

			DongWu dongWuY = cloneMatrixY.getDongWu(i, j);
			dongWuY.setY(j + 1);
			DongWu dongWuAddY = cloneMatrixY.getDongWu(i, j + 1);
			dongWuAddY.setY(j);
			cloneMatrixY.getMatrixXY()[i][j] = dongWuAddY;
			cloneMatrixY.getMatrixXY()[i][j + 1] = dongWuY;

			String yId = dongWuY.getId();
			String yaddId = dongWuAddY.getId();
			boolean ybixu = !yId.equals("TK") && !yaddId.equals("TK");
			boolean ykeyi = yId.equals("WZ") || yaddId.equals("WZ");
			ykeyi = ykeyi || (dongWuY.isHasJQ() && dongWuAddY.isHasJQ());
			ykeyi = ykeyi || eliminate(i, j, cloneMatrixY) || eliminate(i + 1, j, cloneMatrixY);
			
//			if (!dongWuY.getId().equals("TK") && !dongWuAddY.getId().equals("TK") && (eliminate(i, j, cloneMatrixY) || eliminate(i, j + 1, cloneMatrixY))){
			if (ybixu && ykeyi){
				matrixMap.put(new int[]{i, j, 1}, cloneMatrixY);
			}else {
				cloneMatrixY = null;
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

			if (flag) break;
			try {
				flag = dongWu.getId().equals(dongWuNext1.getId()) && dongWu.getId().equals(dongWuNext2.getId());
			} catch (Exception e) {
			}
		}
		return flag;
	}

}
