package com.rahul.bbgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Main {

	private BitBoard WRooks;
	private BitBoard WBishops;
	private BitBoard WPawns;
	private BitBoard WKnights;
	private BitBoard WKing;
	private BitBoard WQueen;

	private BitBoard BRooks;
	private BitBoard BBishops;
	private BitBoard BPawns;
	private BitBoard BKnights;
	private BitBoard BKing;
	private BitBoard BQueen;

	private void initBoards() {
		WRooks = new BitBoard();
		WBishops = new BitBoard();
		WPawns = new BitBoard();
		WKnights = new BitBoard();
		WKing = new BitBoard();
		WQueen = new BitBoard();

		BRooks = new BitBoard();
		BBishops = new BitBoard();
		BPawns = new BitBoard();
		BKnights = new BitBoard();
		BKing = new BitBoard();
		BQueen = new BitBoard();
	}

	public void createBoards(String fen) {
		initBoards();

		int bitIndex = 0;
		char[] fenChars = fen.toCharArray();

		for (int i = 0; i < fenChars.length && bitIndex < BitBoard.BOARD_LENGTH; i++) {
			// System.out.println(fenChars[i]);
			switch (fenChars[i]) {
			case 'R':
				WRooks.flipBit(bitIndex++);
				break;
			case 'N':
				WKnights.flipBit(bitIndex++);
				break;
			case 'B':
				WBishops.flipBit(bitIndex++);
				break;
			case 'P':
				WPawns.flipBit(bitIndex++);
				break;
			case 'K':
				WKing.flipBit(bitIndex++);
				break;
			case 'Q':
				WQueen.flipBit(bitIndex++);
				break;

			case 'r':
				BRooks.flipBit(bitIndex++);
				break;
			case 'n':
				BKnights.flipBit(bitIndex++);
				break;
			case 'b':
				BBishops.flipBit(bitIndex++);
				break;
			case 'p':
				BPawns.flipBit(bitIndex++);
				break;
			case 'k':
				BKing.flipBit(bitIndex++);
				break;
			case 'q':
				BQueen.flipBit(bitIndex++);
				break;
			case '/':
				break;
			default:
				// System.out.println((int) fenChars[i] - 48);
				bitIndex += (int) fenChars[i] - 48;
			}
		}
	}

	public String prettyPrintBoards() {
		StringBuilder builder = new StringBuilder();

		builder.append("White Rooks : ");
		builder.append(WRooks.getBitBoard() + "\n");
		builder.append("White Knights : ");
		builder.append(WKnights.getBitBoard() + "\n");
		builder.append("White Bishops : ");
		builder.append(WBishops.getBitBoard() + "\n");
		builder.append("White Pawns : ");
		builder.append(WPawns.getBitBoard() + "\n");
		builder.append("White King : ");
		builder.append(WKing.getBitBoard() + "\n");
		builder.append("White Queen : ");
		builder.append(WQueen.getBitBoard() + "\n");

		builder.append("Black Rooks : ");
		builder.append(BRooks.getBitBoard() + "\n");
		builder.append("Black Knights : ");
		builder.append(BKnights.getBitBoard() + "\n");
		builder.append("Black Bishops : ");
		builder.append(BBishops.getBitBoard() + "\n");
		builder.append("Black Pawns : ");
		builder.append(BPawns.getBitBoard() + "\n");
		builder.append("Black King : ");
		builder.append(BKing.getBitBoard() + "\n");
		builder.append("Black Queen : ");
		builder.append(BQueen.getBitBoard() + "\n");

		return builder.toString();
	}

	public String printBoards() {
		StringBuilder builder = new StringBuilder();

		builder.append(WRooks.getBitBoard());
		builder.append(WKnights.getBitBoard());
		builder.append(WBishops.getBitBoard());
		builder.append(WPawns.getBitBoard());
		builder.append(WKing.getBitBoard());
		builder.append(WQueen.getBitBoard());

		builder.append(BRooks.getBitBoard());
		builder.append(BKnights.getBitBoard());
		builder.append(BBishops.getBitBoard());
		builder.append(BPawns.getBitBoard());
		builder.append(BKing.getBitBoard());
		builder.append(BQueen.getBitBoard());

		return builder.toString();
	}

	public void buildBoards() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"./data/game4.fen")));
			PrintWriter out = new PrintWriter("./data/game4.bb");
			String fen;

			while ((fen = br.readLine()) != null) {
				createBoards(fen);
				out.println(printBoards());
			}
			br.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.buildBoards();
	}
}
