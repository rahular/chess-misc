package com.rahul.bbgen;

import java.io.Serializable;
import java.util.Arrays;

public class MyDataSetRow implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private double[] WRooks;
	private double[] WBishops;
	private double[] WPawns;
	private double[] WKnights;
	private double[] WKing;
	private double[] WQueen;

	private double[] BRooks;
	private double[] BBishops;
	private double[] BPawns;
	private double[] BKnights;
	private double[] BKing;
	private double[] BQueen;
	
	private double[] evalScore;

	public double[] getRow() {
		return concatAll(WRooks, WBishops, WPawns, WKnights, WKing, WQueen,
				BRooks, BBishops, BPawns, BKnights, BKing, BQueen);
	}

	private double[] concatAll(double[] first, double[]... rest) {
		int totalLength = first.length;
		for (double[] array : rest) {
			totalLength += array.length;
		}
		double[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (double[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public double[] getWRooks() {
		return WRooks;
	}

	public void setWRooks(double[] wRooks) {
		WRooks = wRooks;
	}

	public double[] getWBishops() {
		return WBishops;
	}

	public void setWBishops(double[] wBishops) {
		WBishops = wBishops;
	}

	public double[] getWPawns() {
		return WPawns;
	}

	public void setWPawns(double[] wPawns) {
		WPawns = wPawns;
	}

	public double[] getWKnights() {
		return WKnights;
	}

	public void setWKnights(double[] wKnights) {
		WKnights = wKnights;
	}

	public double[] getWKing() {
		return WKing;
	}

	public void setWKing(double[] wKing) {
		WKing = wKing;
	}

	public double[] getWQueen() {
		return WQueen;
	}

	public void setWQueen(double[] wQueen) {
		WQueen = wQueen;
	}

	public double[] getBRooks() {
		return BRooks;
	}

	public void setBRooks(double[] bRooks) {
		BRooks = bRooks;
	}

	public double[] getBBishops() {
		return BBishops;
	}

	public void setBBishops(double[] bBishops) {
		BBishops = bBishops;
	}

	public double[] getBPawns() {
		return BPawns;
	}

	public void setBPawns(double[] bPawns) {
		BPawns = bPawns;
	}

	public double[] getBKnights() {
		return BKnights;
	}

	public void setBKnights(double[] bKnights) {
		BKnights = bKnights;
	}

	public double[] getBKing() {
		return BKing;
	}

	public void setBKing(double[] bKing) {
		BKing = bKing;
	}

	public double[] getBQueen() {
		return BQueen;
	}

	public void setBQueen(double[] bQueen) {
		BQueen = bQueen;
	}

	public double[] getEvalScore() {
		return evalScore;
	}

	public void setEvalScore(double[] evalScore) {
		this.evalScore = evalScore;
	}
}
