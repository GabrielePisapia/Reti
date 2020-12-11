package com.sm.reti.fabrikam_functions;

public class Score {
	private int countPositive;
	private int countNegative;
	private int countNeutral;
	private int countMixed;
	
	private int totalCount;
	
	public Score() {
		this.countMixed = 0;
		this.countNegative = 0;
		this.countNeutral = 0;
		this.countPositive = 0;
	}
	
	public void addPositive() {
		this.countPositive++;
		this.totalCount++;
	}
	
	public void addNegative() {
		this.countNegative++;
		this.totalCount++;
	}
	
	public void addNeutral() {
		this.countNeutral++;
		this.totalCount++;
	}
	
	public void addMixed() {
		this.countMixed++;
		this.totalCount++;
	}
	
	public int getCountPositive() {
		return countPositive;
	}

	public int getCountNegative() {
		return countNegative;
	}

	public int getCountNeutral() {
		return countNeutral;
	}

	public int getCountMixed() {
		return countMixed;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public float[] getAvg() {
		float[] res = new float[4];
		res[0] = this.countPositive/this.totalCount;
		res[1] = this.countNegative/this.totalCount;
		res[2] = this.countNeutral/this.totalCount;
		res[3] = this.countMixed/this.totalCount;
		return res;
	}
}
