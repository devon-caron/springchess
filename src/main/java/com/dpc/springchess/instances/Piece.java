package com.dpc.springchess.instances;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Piece {
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	
	public enum PieceType {
		KING,
		QUEEN,
		ROOK,
		BISHOP,
		KNIGHT,
		PAWN,
		EMPTY
	}
	
	private int color;
	private PieceType type;
	
	public static Piece pieceBuilder(char pieceChar) {
		Piece myPiece = new Piece();
		PieceType myType;
		int color;
		char myPieceChar = pieceChar;
		
		// if fen piece is lowercase the piece is black, otherwise white
		if (pieceChar > 91) {
			color = BLACK;
			myPieceChar -= 32; // Sets the character to the uppercase, which is 32 indexes down on ascii table, for later
		} else {
			color = WHITE;
		}
		
		switch(myPieceChar) {
		case 'K': 
			myType = PieceType.KING;
		case 'Q':
			myType = PieceType.QUEEN;
		case 'R':
			myType = PieceType.ROOK;
		case 'B':
			myType = PieceType.BISHOP;
		case 'N':
			myType = PieceType.KNIGHT;
		case 'P':
			myType = PieceType.PAWN;
		default:
			myType = PieceType.EMPTY;
		}
		
		myPiece.setColor(color);
		myPiece.setType(myType);
		
		return myPiece;
	}
}
