package com.rahul.stockfish;


public class StockfishTest {
	public static void main(String[] args) {
		Stockfish client = new Stockfish();
		// initialize and connect to engine
		if (client.startEngine()) {
			System.out.println("Engine has started..");
		} else {
			System.out.println("Oops! Something went wrong..");
		}
		// send commands manually
		client.sendCommand("uci");
		// receive output dump
		System.out.println(client.getOutput(0));
		// get the best move for a position with a given think time
		System.out.println(client.getBestMove(
				"8/6pk/8/1R5p/3K3P/8/6r1/8 b - - 0 42", 1000));
		// stop the engine
		client.stopEngine();
	}
}
