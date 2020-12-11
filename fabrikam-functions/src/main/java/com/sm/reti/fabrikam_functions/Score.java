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
		this.totalCount = 0;
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
		float cps = (float) countPositive;
		float cng = (float) countNegative;
		float mxd = (float) countMixed;
		float ttc = (float) totalCount;
		res[0] = cps*100/ttc;
		res[1] = cng*100/ttc;
		res[2] = 0;
		res[3] = mxd*100/ttc;
		return res;
	}
}
