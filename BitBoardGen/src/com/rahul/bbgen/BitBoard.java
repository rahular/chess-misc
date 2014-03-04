package com.rahul.bbgen;

import java.util.BitSet;

/**
 * This class takes in FEN strings which start from H1 and end at A8
 * 
 * @author rahul
 */
public class BitBoard {

	private BitSet bitBoard;
	public static final int BOARD_LENGTH = 64;

	public BitBoard() {
		bitBoard = new BitSet(BOARD_LENGTH);
	}

	public void flipBit(int index) {
		if (index >= 0 && index < BOARD_LENGTH)
			bitBoard.flip(index);
	}

	public String getPrettyBitBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < BOARD_LENGTH; i++) {
			builder.append(bitBoard.get(i) ? 1 : 0);
		}

		return builder.toString();
	}
	
	/**
	 * This method returns an array of doubles which can be fed directly into a
	 * neural network
	 * 
	 * @return array of doubles
	 * @author rahul
	 */
	public double[] getBitBoard() {
		double[] array = new double[BOARD_LENGTH];
		for (int i = 0; i < BOARD_LENGTH; i++) {
			if (bitBoard.get(i))
				array[i] = 1.0;
			else
				array[i] = 0.0;
		}
		return array;
	}

	public static void main(String[] args) {
		BitBoard bb = new BitBoard();
		System.out.println(bb.getBitBoard());
	}
}
