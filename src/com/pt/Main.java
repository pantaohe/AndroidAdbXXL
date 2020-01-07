package com.pt;

import java.util.Map;

import com.pt.jo.DongWu;
import com.pt.po.Matrix;
import com.pt.utils.ConnAndroid;
import com.pt.utils.Exec;
import com.pt.utils.matrix.MatrixCreate;
import com.pt.utils.matrix.MatrixMove;
import com.pt.utils.matrix.MatrixScore;

public class Main {

	public static void main(String[] args) {
//		ConnAndroid.connAnd();
//		ConnAndroid.dpi();
		//建立xxl矩阵
		Matrix matrix = MatrixCreate.createMatrix();
		System.out.println(matrix.toStringName());
//		Map<int[], Matrix> matrixMap = MatrixMove.matrixMove(matrix);
//		
//		int[] matrixTopScore = MatrixScore.matrixTopScore(matrixMap);
//		
//		swipe(matrixTopScore, matrix);
		

	}

	private static void swipe(int[] matrixTopScore, Matrix matrix) {

		DongWu dongWu = matrix.getDongWu(matrixTopScore[0], matrixTopScore[1]);
		DongWu dongWuX = null;
		if (matrixTopScore[2] == 0) {
			dongWuX = matrix.getDongWu(matrixTopScore[0] + 1, matrixTopScore[1]);
		}else {
			dongWuX = matrix.getDongWu(matrixTopScore[0], matrixTopScore[1] + 1);
		}
		Exec.exec("shell input swipe " + dongWu.getPxXY() + dongWuX.getPxXY() + "200");
		System.out.println("得分：" + matrixTopScore[3]);
	}

	

	


	

	

	
	
	
}
