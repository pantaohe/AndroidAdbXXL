package com.pt.jo;

import java.io.Serializable;

public class RGB implements Serializable{
	private static final long serialVersionUID = 1L;
	private int r;
	private int g;
	private int b;
	
	public RGB(int rgb){
		this.r = rgb >> 16 & 0xff;
		this.g = rgb >> 8 & 0xff;
		this.b = rgb & 0xff;
	}
	public RGB(int[] rgbs){
		this.r = rgbs[0];
		this.g = rgbs[1];
		this.b = rgbs[2];
	}
	public RGB(int[] rgbs, int par){
		this.r = rgbs[0]/par;
		this.g = rgbs[1]/par;
		this.b = rgbs[2]/par;
	}

	public int getSumRGB(){
		return this.r + this.g + this.b;
	}
	public int getSumRG(){
		return this.r + this.g;
	}
	public int getSumRB(){
		return this.r + this.b;
	}
	public int getSumGB(){
		return this.g + this.b;
	}
	
	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "[r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
}
