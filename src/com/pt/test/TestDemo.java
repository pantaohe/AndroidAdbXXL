package com.pt.test;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.pt.config.Constant;
import com.pt.jo.RGB;

public class TestDemo {
	public static void main(String[] args) {
		
		try {
			File file = new File("../img/xxl/screen3.png");
			
			BufferedImage image = ImageIO.read(file);

			int height = image.getHeight();
			int width = image.getWidth();
			System.out.println(width + "x" + height);
			RGB[] printfImg = null;
			List<RGB[]> list = new ArrayList<RGB[]>();
			
			printfImg = printfImg(image, 3, 3);
			list.add(printfImg);
			
			maxRGB(list);
			
			
//			File fileJq = new File("resources/img/temp/tian.png");
//			if (!fileJq.exists()) {
//				fileJq.createNewFile();
//			}
//			
//			BufferedImage cut = cut(file, 0, 545, 1080, 1080);
//			//bufferedimage 转换成 inputstream
//			ByteArrayOutputStream bs = new ByteArrayOutputStream(); 
//			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
//			ImageIO.write(cut, "png", imOut); 
//			InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
//
//			OutputStream outStream = new FileOutputStream(fileJq);
//			
//			byte[] b = new byte[1024];
//			int len = 0;
//			while((len = inputStream.read(b)) != -1) {
//				outStream.write(b, 0, len);
//			}
//			
//			inputStream.close();
//			outStream.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void maxRGB(List<RGB[]> list) {
		int maxR1 = 0, minR1 = Integer.MAX_VALUE, maxG1 = 0, minG1 = Integer.MAX_VALUE, maxB1 = 0, minB1 = Integer.MAX_VALUE; 
		int maxR2 = 0, minR2 = Integer.MAX_VALUE, maxG2 = 0, minG2 = Integer.MAX_VALUE, maxB2 = 0, minB2 = Integer.MAX_VALUE; 
		int maxR3 = 0, minR3 = Integer.MAX_VALUE, maxG3 = 0, minG3 = Integer.MAX_VALUE, maxB3 = 0, minB3 = Integer.MAX_VALUE; 
		
		for (RGB[] rgbs : list) {
			int r1 = rgbs[0].getR(); if (r1 > maxR1) maxR1 = r1; if (r1 < minR1) minR1 = r1;
			int r2 = rgbs[1].getR(); if (r2 > maxR2) maxR2 = r2; if (r2 < minR2) minR2 = r2;
			int r3 = rgbs[2].getR(); if (r3 > maxR3) maxR3 = r3; if (r3 < minR3) minR3 = r3;
			
			int g1 = rgbs[0].getG(); if (g1 > maxG1) maxG1 = g1; if (g1 < minG1) minG1 = g1;
			int g2 = rgbs[1].getG(); if (g2 > maxG2) maxG2 = g2; if (g2 < minG2) minG2 = g2;
			int g3 = rgbs[2].getG(); if (g3 > maxG3) maxG3 = g3; if (g3 < minG3) minG3 = g3;
			
			int b1 = rgbs[0].getB(); if (b1 > maxB1) maxB1 = b1; if (b1 < minB1) minB1 = b1;
			int b2 = rgbs[1].getB(); if (b2 > maxB2) maxB2 = b2; if (b2 < minB2) minB2 = b2;
			int b3 = rgbs[2].getB(); if (b3 > maxB3) maxB3 = b3; if (b3 < minB3) minB3 = b3;
		}
		System.out.println("最大值与最小值");
		System.out.println("内层：" + maxR1 + "," + minR1 + "\t"  + maxG1 + "," + minG1 + "\t"  + maxB1 + "," + minB1);
		System.out.println("外层：" + maxR2 + "," + minR2 + "\t"  + maxG2 + "," + minG2 + "\t"  + maxB2 + "," + minB2);
		System.out.println("外圈：" + maxR3 + "," + minR3 + "\t"  + maxG3 + "," + minG3 + "\t"  + maxB3 + "," + minB3);
	}


	private static RGB[] printfImg(BufferedImage image, int i1, int j1) {
		int i = j1 * 120;
		int j = 545 + i1 * 120;
		System.out.println((j-425)/120 + "行" + (i/120 + 1) + "列");
		
		int[] pxSumIn = new int[3];
		int[] pxSumOut = new int[3];
		int[] pxSumOuterRing = new int[3];
		int rgb = 0;
		int in = 0;
		int out = 0;
		int outer = 0;
		long time = System.currentTimeMillis();
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
		System.out.println("内层：R=" + pxSumIn[0]/in + ";G=" + pxSumIn[1]/in + ";B=" + pxSumIn[2]/in);
		System.out.println("外层：R=" + pxSumOut[0]/out + ";G=" + pxSumOut[1]/out + ";B=" + pxSumOut[2]/out);
		System.out.println("外圈：R=" + pxSumOuterRing[0]/outer + ";G=" + pxSumOuterRing[1]/outer + ";B=" + pxSumOuterRing[2]/outer);
		System.out.println("out=" + out + "in=" + in + "outer=" + outer);
		System.out.println(System.currentTimeMillis() - time);
		RGB rgbs1 = new RGB(pxSumIn, in);
		RGB rgbs2 = new RGB(pxSumOut, out);
		RGB rgbs3 = new RGB(pxSumOuterRing, outer);
		return new RGB[] {rgbs1, rgbs2, rgbs3};
		
	}
	private static boolean getInScope(int l, int k, int rgbOutPx) {
		return Math.abs(l - Constant.SPEED/2) < rgbOutPx && Math.abs(k - Constant.SPEED/2) < rgbOutPx;
	}
	private static boolean equal(int l, int k, int par) {
		return l == par || k == par || (Constant.SPEED - l) == par || (Constant.SPEED - k) == par;
	}
	//主要算左右两边，上下两边因为不同关卡的起点像素位置不同，所以上下两边多去掉了一个pxMove
	private static boolean getOutScope(int l, int k, int pxMove, int rgbOutPx) {
		return (l + k < rgbOutPx || Constant.SPEED - l + k < rgbOutPx || 
					Constant.SPEED - k + l < rgbOutPx || Constant.SPEED * 2 - k - l < rgbOutPx) && 
				(l > pxMove && k > pxMove * 2 && (Constant.SPEED - l) > pxMove && (Constant.SPEED - k) > pxMove * 2);
	}

	/**
     * 函 数 名: cut<br>
     * 功能描述： <br>
     * 创 建 人: 王国庆<br>
     * 日 期: 2013-2-22<br>
     * 输入输出:
     * 
     * @param image
     *            内存中的图片 BufferedImage对象
     * @param x
     *            开始剪切的x坐标
     * @param y
     *            开始剪切的y坐标
     * @param width
     *            剪切的宽度
     * @param height
     *            剪切的高度
     * @return<br>
     */
	private static BufferedImage cut(File image, int x, int y, int width, int height) {
		try {

			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("png");
			/**因为是内存中的图片对象，所以没有后缀，就给一个jpg后缀，我给png后缀出错，不知是不是我的BufferedImage对象不对*/
			ImageReader reader = it.next();
			// 获取图片流

			ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(image));

			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);
			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();
			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);

			iis.close();

			return bi;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


}