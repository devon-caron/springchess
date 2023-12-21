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
		PieceType myType = PieceType.EMPTY;
		int color;
		char myPieceChar = pieceChar;
		
		// if fen piece is lowercase the piece is black, otherwise white
		if (pieceChar > 91) {
			color = BLACK;
			myPieceChar -= 32; // Sets the character to the uppercase, which is 32 indexes down on ascii table, for later
		} else {
			color = WHITE;
		}
		System.out.println("testing " + myPieceChar);
		if (myPieceChar == 'K')
			myType = PieceType.KING;
		else if (myPieceChar == 'Q') 
			myType = PieceType.QUEEN;
		else if (myPieceChar == 'R') 
			myType = PieceType.ROOK;
		else if (myPieceChar == 'B')
			myType = PieceType.BISHOP;
		else if (myPieceChar == 'N') 
			myType = PieceType.KNIGHT;
		else if (myPieceChar == 'P') 
			myType = PieceType.PAWN;
		else 
			myType = PieceType.EMPTY;
		
		
		myPiece.setColor(color);
		myPiece.setType(myType);
		
		System.out.println("TYPE AT SET: " + myType);
		
		return myPiece;
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		
		if (color == WHITE) {
			toReturn += "w";
		} else {
			toReturn += "b";
		}
		
		switch(type) {
		case KING:
			return toReturn + 'k';
		case QUEEN:
			return toReturn + 'q';
		case ROOK:
			return toReturn + 'r';
		case BISHOP:
			return toReturn + 'b';
		case KNIGHT:
			return toReturn + 'n';
		case PAWN:
			return toReturn + 'p';
		default:
			System.out.println("TYPE : " + type);
			return "ERROR";
					
		}
	}
}
