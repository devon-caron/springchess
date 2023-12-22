package com.dpc.springchess.instances;

public class RunBoardInstance {
	
	public static void main(String[] args) {
		String myFen = "rnbqkbnr/pppppppp/8/4r3/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1";
		BoardInstance myBoard = new BoardInstance(myFen);
		//myBoard.print();
		myBoard.printMoves();
	}
}
