package com.pt.config;

public class Constant {

	public static final boolean DEBUG = false;
	public static final String DEBUG_IMG_PATH = "../img/xxl/level/screen2.png";
	
	public static final String TEMP_PATH = "resources/img/temp";
	
	public static int WIDTH_PX = 0;
	public static int HEIGHT_PX = 0;
	public static int SPEED = 1;
	
	public static final int X_START = 0;
	public static int X_END = 0;
	public static final int X_COUNT = 9;
	
	public static int Y_START = 0;//545
	public static int Y_END = 0;//1625
	public static int Y_COUNT = 0;//9

	
	public static final int OUTER_RING = 6;//区哪一个像素点的外圈
	public static final int PX_MOVE = 8;//从哪一个像素点开始向里面获取数据
	public static final int RGB_OUT_PX = 30;//外面部分像素点个数
	public static final int RGB_IN_PX = 40;//里面部分像素点个数
	public static final int COLOUR_ERROR = 25;//颜色误差值

}
