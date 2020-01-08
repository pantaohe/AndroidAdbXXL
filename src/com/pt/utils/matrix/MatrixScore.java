package com.pt.utils.matrix;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.pt.config.Constant;
import com.pt.po.Matrix;
import com.pt.po.dw.Tian;
import com.pt.utils.cloner.ObjClonerSeiz;

public class MatrixScore {

	public static int[] matrixTopScore(Map<int[], Matrix> matrixMap) {
		int score = 0;
		int[] xyz = new int[3];
		for (Entry<int[], Matrix> entry : matrixMap.entrySet()) {
			Matrix matrix = entry.getValue();
			int score2 = score(matrix);
			if (score2 > score) {
				score = score2;
				xyz = entry.getKey();
			}
//			System.out.println(entry.getKey()[0] + "行" + entry.getKey()[1] + "列" + entry.getKey()[2] + "方向;得分：" + score);
		}
		return new int[] {xyz[0], xyz[1], xyz[2], score};
	}

	public static int score(Matrix matrix) {
		
		int score = 0;
		Matrix matrixMove = ObjClonerSeiz.CloneObj(matrix);
		
		Set<Integer> set = new HashSet<Integer>();
		//逐行遍历
		for (int i = 0; i < Constant.Y_COUNT; i++) {
			for (int j = 0; j < Constant.X_COUNT - 2; ) {
				String dongWuId = matrix.getDongWu(i, j).getId();
				int k = 0;
				String dongWuId1 = "";
				try {
					do {
						k++;
						dongWuId1 = matrix.getDongWu(i, j + k).getId();
					} while (!dongWuId.equals("TK") && dongWuId.equals(dongWuId1));
				} catch (Exception e) {
				}
				
				if (k > 2) {
					for (int l = 0; l < k; l++) {
						set.add(i * 100 + j + l);
					}
					score += k;
				}
				
				j += k;
			}
		}
		
		//逐列遍历
		for (int j = 0; j < Constant.X_COUNT; j++) {
			for (int i = 0; i < Constant.Y_COUNT - 2; ) {
				
				String dongWuId = matrix.getDongWu(i, j).getId();
				int k = 0;
				String dongWuId1 = "";
				try {
					do {
						k++;
						dongWuId1 = matrix.getDongWu(i + k, j).getId();
					} while (!dongWuId.equals("TK") && dongWuId.equals(dongWuId1));
				} catch (Exception e) {
				}
				
				if (k > 2) {
					for (int l = 0; l < k; l++) {
						set.add((i + l) * 100 + j);
					}
					
					score += k;
				}
				
				i += k;
			}
		}
		for (Integer integer : set) {
			int x = integer / 100;
			int y = integer % 100;
			for (int i = x; i > 0; i--) {
				matrixMove.moveDown(i - 1, y);
			}
			matrixMove.getMatrixXY()[0][y] = new Tian(0, y);
		}
		
		if (score != 0) {
			score += score(matrixMove);
		}else {
			matrixMove = null;
		}
		
		return score;
	}
	
}
