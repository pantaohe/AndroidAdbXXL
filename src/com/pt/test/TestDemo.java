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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.pt.Main;
import com.pt.config.Constant;

public class TestDemo {
	public static void main(String[] args) {
		
		try {
			File file = new File("resources/img/temp/screen2.png");
			
			BufferedImage image = ImageIO.read(file);

			int height = image.getHeight();
			int width = image.getWidth();
			System.out.println(width + "x" + height);
			printfImg(image, 360, 545);
//			printfImg(image, 120, 545);
//			printfImg(image, 120, 665);
//			printfImg(image, 120, 785);
//			printfImg(image, 240, 665);
//			printfImg(image, 360, 665);
//			printfImg(image, 360, 1505);
			
			
//			File fileJq = new File("resources/img/temp/tian.png");
//			if (!fileJq.exists()) {
//				fileJq.createNewFile();
//			}
			
//			BufferedImage cut = cut(file, 120, 545, 840, 1080);
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


	private static void printfImg(BufferedImage image, int i, int j) {
		System.out.println((j-550)/120 + "行" + (i/120 + 1) + "列");
		
		int[] pxSumIn = new int[3];
		int[] pxSumOut = new int[3];
		int rgb = 0;
		int in = 0;
		int out = 0;
		long time = System.currentTimeMillis();
		for (int j2 = Constant.PX_MOVE; j2 < Constant.SPEED - Constant.PX_MOVE; j2 += 2) {
			for (int k = Constant.PX_MOVE; k < Constant.SPEED - Constant.PX_MOVE; k += 2) {
				rgb = image.getRGB(i + j2, j + k);
//				if (j2 < Constant.PX_MOVE || j2 > Constant.SPEED - Constant.PX_MOVE || 
//					k < Constant.PX_MOVE || k > Constant.SPEED - Constant.PX_MOVE) {
				if (j2 + k < Constant.RGB_OUT_PX || Constant.SPEED - j2 + k < Constant.RGB_OUT_PX ||
						Constant.SPEED - k + j2 < Constant.RGB_OUT_PX || Constant.SPEED * 2 - k - j2 < Constant.RGB_OUT_PX) {
					out ++;
					pxSumOut[0] += rgb >> 16 & 0xff;
					pxSumOut[1] += rgb >> 8 & 0xff;
					pxSumOut[2] += rgb & 0xff;
				}else {
					in ++;
					pxSumIn[0] += rgb >> 16 & 0xff;
					pxSumIn[1] += rgb >> 8 & 0xff;
					pxSumIn[2] += rgb & 0xff;
				}
			}
		}
		System.out.println("内层：R=" + pxSumIn[0]/in + ";G=" + pxSumIn[1]/in + ";B=" + pxSumIn[2]/in);
		System.out.println("外层：R=" + pxSumOut[0]/out + ";G=" + pxSumOut[1]/out + ";B=" + pxSumOut[2]/out);
		System.out.println("out=" + out + "in=" + in);
		System.out.println(System.currentTimeMillis() - time);
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