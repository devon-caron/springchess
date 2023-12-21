package com.dpc.springchess.instances;

public class RunBoardInstance {
	
	public static void main(String[] args) {
		BoardInstance myBoard = new BoardInstance();
		myBoard.print();
		myBoard.printMoves();
	}
}
