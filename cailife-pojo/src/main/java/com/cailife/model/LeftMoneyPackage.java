package com.cailife.model;

public class LeftMoneyPackage {

	private int remainSize;
	
	private double remainMoney;
	
	public LeftMoneyPackage() {}
	
	public LeftMoneyPackage(int remainSize, double remainMoney) {
		this.remainMoney = remainMoney;
		this.remainSize = remainSize;
	}

	public int getRemainSize() {
		return remainSize;
	}

	public void setRemainSize(int remainSize) {
		this.remainSize = remainSize;
	}

	public double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(double remainMoney) {
		this.remainMoney = remainMoney;
	}
	
	
}
