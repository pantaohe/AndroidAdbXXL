package com.pt.config;

public class Constant {

	public static final boolean DEBUG = true;
	
	public static final String TEMP_PATH = "resources/img/temp";
	
//	public static final int X_START = 120;
//	public static final int X_END = 960;
//	public static final int X_COUNT = 7;
//	
//	public static final int Y_START = 670;
//	public static final int Y_END = 1390;
//	public static final int Y_COUNT = 6;
	public static final int X_START = 0;
	public static final int X_END = 1080;
	public static final int X_COUNT = 9;
	
	public static final int Y_START = 545;
	public static final int Y_END = 1625;
	public static final int Y_COUNT = 9;

	public static final int SPEED = 120;
	
	public static final int OUTER_RING = 6;//区哪一个像素点的外圈
	public static final int PX_MOVE = 8;//从哪一个像素点开始向里面获取数据
	public static final int RGB_OUT_PX = 30;//外面部分像素点个数
	public static final int RGB_IN_PX = 40;//里面部分像素点个数
	public static final int COLOUR_ERROR = 21;//颜色误差值

}
