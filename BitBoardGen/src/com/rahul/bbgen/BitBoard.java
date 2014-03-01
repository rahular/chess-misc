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

	public String getBitBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < BOARD_LENGTH; i++) {
			builder.append(bitBoard.get(i) ? 1 : 0);
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		BitBoard bb = new BitBoard();
		System.out.println(bb.getBitBoard());
	}
}
