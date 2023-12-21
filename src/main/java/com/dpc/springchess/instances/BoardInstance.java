package com.dpc.springchess.instances;

import java.util.ArrayList;
import java.util.List;

import com.dpc.springchess.instances.Piece.PieceType;

public class BoardInstance {

	public static final String DEFAULT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	public static final String TEST_FEN = "rnbqk2r/pppp1ppp/8/1b2p3/8/8/PPPPPPPP/RNBQK2R b KQkq - 0 1";
	
	private String myFen;

	private boolean whiteKingSideCastle;
	private boolean whiteQueenSideCastle;
	private boolean blackKingSideCastle;
	private boolean blackQueenSideCastle;

	private boolean isWhitesTurn;

	private int halfMoveClock;
	private int moveCounter;

	private String validPassantSquare;

	private Piece[][] position = new Piece[8][8];

	public BoardInstance() {
		this.myFen = DEFAULT_FEN;
		init(DEFAULT_FEN);
	}

	public BoardInstance(String fen) {
		this.myFen = fen;
		init(fen);
	}

	public BoardInstance(BoardInstance existing) {
		this.myFen = existing.getFen();
		init(this.myFen);
	}

	public void init(String fen) {
		fillPositions(fen);
		setDefaults(fen);
		print();
	}

	public void fillPositions(String fen) {

	    int counter = 56; // Starting index is 56 (or [7][0] or a8) which we will start with. The reason for this is that the FEN starts with a8 and it is easier to populate the array
	    int slashCounter = 0;                   // in this way.
	    int index;
	    for (index = 0; fen.charAt(index) != ' '; ++index) { // This section of the code only checks for positions on the chessboard.
//	        std::cout << "Counter: " << counter << std::endl;
	        if (fen.charAt(index) == ' ') {
//	            std::cout << "blank space" << std::endl;
	        }
	        else if (fen.charAt(index) == '/') {
//	            std::cout << "slash op" << std::endl;
	            slashCounter++;
	            counter -= 16; // Whenever a slash occurs, that's the end of the rank. -16 tells the program to go to the beginning of the previous rank.
	        }
	        else if (fen.charAt(index) >= 48 && fen.charAt(index) <= 57) {
	        	// Handles numbers in the FEN code
	            counter += (fen.charAt(index) - 48); // ASCII value of 0 character (48) subtracted from the final value.
//	            std::cout << counter << std::endl;
	        }
	        else if (fen.charAt(index) == 'P' || fen.charAt(index) == 'p') {
	            place(fen.charAt(index), counter, PieceType.PAWN);
	            counter++;
	        }
	        else if (fen.charAt(index) == 'R' || fen.charAt(index) == 'r') {
	            place(fen.charAt(index), counter, PieceType.ROOK);
	            counter++;

	        }
	        else if (fen.charAt(index) == 'N' || fen.charAt(index) == 'n') {
	            place(fen.charAt(index), counter, PieceType.KNIGHT);
	            counter++;

	        }
	        else if (fen.charAt(index) == 'B' || fen.charAt(index) == 'b') {
	            place(fen.charAt(index), counter, PieceType.BISHOP);
	            counter++;

	        }
	        else if (fen.charAt(index) == 'Q' || fen.charAt(index) == 'q') {
	            place(fen.charAt(index), counter, PieceType.QUEEN);
	            counter++;

	        }
	        else if (fen.charAt(index) == 'K' || fen.charAt(index) == 'k') {
	            place(fen.charAt(index), counter, PieceType.KING);
	            counter++;
	        }
	    }

	}

	public void setDefaults(String fen) {
		// Index: variable | 0: position string | 1: turnStr | 2: castlingStr | 3: passantStr | 4: halfMoveStr | 5: moveCounterStr
		String[] data = fen.split(" ");
		
		this.isWhitesTurn = (data[1].equals("w") ? true : false);

		this.whiteKingSideCastle = false;
		this.whiteQueenSideCastle = false;
		this.blackKingSideCastle = false;
		this.blackQueenSideCastle = false;
		for (int i = 0; i < data[2].length(); i++) {
			if (data[2].charAt(i) == 'K') this.whiteKingSideCastle = true;
			if (data[2].charAt(i) == 'Q') this.whiteQueenSideCastle = true;
			if (data[2].charAt(i) == 'k') this.blackKingSideCastle = true;
			if (data[2].charAt(i) == 'q') this.blackQueenSideCastle = true;
		}
		
		this.validPassantSquare = data[3];
		this.halfMoveClock = Integer.parseInt(data[4]);
		this.moveCounter = Integer.parseInt(data[5]);
		
	}

	public List<String> getValidMoves(int color) {
		List<String> moveList = new ArrayList<>();
		int myColor = color;
		int otherColor = (myColor == Piece.BLACK ? Piece.WHITE : Piece.BLACK);
		
		if (myColor == Piece.WHITE) {
		    // Kingside
		    if (whiteKingSideCastle) {
		        if (/*e1*/position[0][4].getType() == PieceType.KING && position[0][4].getColor() == Piece.WHITE && !isInCheck(0, 4, Piece.WHITE)) {
		            if (/*h1*/position[0][7].getType() == PieceType.ROOK && position[0][7].getColor() == Piece.WHITE) {
		                if (/*g1*/position[0][6] == null && !isInCheck(0, 6, Piece.WHITE) && /*f1*/position[0][5] == null && !isInCheck(0, 5, Piece.WHITE)) {
		                    moveList.add("e1->O-O");
		                }
		            }
		        }
		    }
		    if (whiteQueenSideCastle) {
		        if (/*e1*/position[0][4].getType() == PieceType.KING && position[0][4].getColor() == Piece.WHITE && !isInCheck(0, 4, Piece.WHITE)) {
		            if (/*a1*/position[0][0].getType() == PieceType.ROOK && position[0][0].getColor() == Piece.WHITE) {
		                if (/*d1*/position[0][3] == null && !isInCheck(0, 3, Piece.WHITE) && /*c1*/position[0][2] == null && !isInCheck(0, 2, Piece.WHITE) && /*b1*/position[0][1] == null) {
		                    moveList.add("e1->O-O-O");
		                }
		            }
		        }
		    }
		}
		else if (color == Piece.BLACK) {
		    // Kingside
		    if (blackKingSideCastle) {
		        if (/*e8*/position[7][4].getType() == PieceType.KING && position[7][4].getColor() == Piece.BLACK && !isInCheck(7, 4, Piece.BLACK)) {
		            if (/*h8*/position[7][7].getType() == PieceType.ROOK && position[7][7].getColor() == Piece.BLACK) {
		                if (/*g8*/position[7][6] == null && !isInCheck(7, 6, Piece.BLACK) && /*f8*/position[7][5] == null && !isInCheck(7, 5, Piece.BLACK)) {
		                    moveList.add("e8->O-O");
		                }
		            }
		        }
		    }
		    if (blackQueenSideCastle) {
		        if (/*e8*/position[7][4].getType() == PieceType.KING && position[7][4].getColor() == Piece.BLACK && !isInCheck(7, 4, Piece.BLACK)) {
		            if (/*a8*/position[7][0].getType() == PieceType.ROOK && position[7][0].getColor() == Piece.BLACK) {
		                if (/*d8*/position[7][3] == null && !isInCheck(7, 3, Piece.BLACK) && /*c8*/position[7][2] == null && !isInCheck(7, 2, Piece.BLACK) && /*b8*/position[7][1] == null) {
		                    moveList.add("e8->O-O-O");
		                }
		            }
		        }
		    }
		}
		

	    for (int count = 0; count < 64; count++) {
	        int rank = count / 8;
	        int file = count % 8;
	        char fileLetter = (char) (file + 97);	
	        //==========================================================================================
	        // The code right below currPosStr is optional, highlighting where the piece's original square is located. 
	        //==========================================================================================
	        String currPosStr = "";
//	        currPosStr += fileLetter; // optional
//	        currPosStr += (rank + 1); // optional
//	        currPosStr += "->";       // optional
	        Piece currPiece = position[rank][file];


	        if (currPiece == null) {
	            // Do Nothing
	        }
	        else {
	            if (currPiece.getColor() == color) {
	                if (currPiece.getType() == PieceType.PAWN) {
	                    if (currPiece.getColor() == Piece.BLACK) {
	                        if (rank == 6) { // 7th rank of the chessboard
	                            if (position[rank - 2][file] == null && position[rank - 1][file] == null) {
	                                String toPush = genMoveStr(PieceType.PAWN, (rank - 2), file, false); // checks 2 spaces in front of the pawn
	                                moveList.add(currPosStr + toPush);                      //1 added here since chess is 1-8 (a-h) and we are 
	                            }                                                    // working with 0-7, similar done for the rest
	                        }
	                        if (position[rank - 1][file] == null && rank != 1) {
	                            String toPush = genMoveStr(PieceType.PAWN, (rank - 1), file, false);
	                            moveList.add(currPosStr + toPush);
	                        }
	                        if (position[rank - 1][file] == null && rank == 1) {
	                            String toPush = genMoveStr(PieceType.PAWN, (rank - 1), file, false);
	                            moveList.add(currPosStr + toPush + "=Q");
	                            moveList.add(currPosStr + toPush + "=R");
	                            moveList.add(currPosStr + toPush + "=B");
	                            moveList.add(currPosStr + toPush + "=N");
	                        }
	                        if (checkIfValidSquare(rank-1, file-1) && position[rank-1][file-1] != null 
	                        		&& (position[rank - 1][file - 1].getColor() == otherColor 
	                        		|| ((rank - 1) * 8 + (file - 1)) == posToNum(validPassantSquare))) {
	                            String toPush = genMoveStr(PieceType.PAWN, (rank - 1), (file - 1), true);
	                            if (rank == 1) {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=Q");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=R");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=B");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=N");
	                            }
	                            else {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush);
	                            }
	                        }
	                        if (checkIfValidSquare(rank-1, file+1) && position[rank-1][file+1] != null 
	                        		&& (position[rank - 1][file + 1].getColor() == otherColor 
	                        		|| ((rank - 1) * 8 + (file + 1)) == posToNum(validPassantSquare))) {
	                            String toPush = genMoveStr(PieceType.PAWN, (rank - 1), (file + 1), true);
	                            if (rank == 1) {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=Q");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=R");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=B");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=N");
	                            }
	                            else {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush);
	                            }
	                        }
	                    }
	                    else {
	                        if (rank == 1) { // 2nd rank of the chessboard
	                            if (position[rank + 2][file] == null && position[rank + 1][file] == null) { // checks 2 spaces in front of the pawn
	                                String toPush = genMoveStr(PieceType.PAWN, (rank + 2), file, false);
	                                moveList.add(currPosStr + toPush);
	                            }
	                        }
	                        if (position[rank + 1][file] == null && rank != 6) {
	                            String toPush = genMoveStr(PieceType.PAWN, (rank + 1), file, false);
	                            moveList.add(currPosStr + toPush);
	                        }
	                        if (position[rank + 1][file] == null && rank == 6) {
	                            String toPush = genMoveStr(PieceType.PAWN, (rank + 1), file, false);
	                            moveList.add(currPosStr + toPush + "=Q");
	                            moveList.add(currPosStr + toPush + "=R");
	                            moveList.add(currPosStr + toPush + "=B");
	                            moveList.add(currPosStr + toPush + "=N");
	                        }

//	                        std::cout << "SQUARETONUM: " << posToNum(validPassantSquare) << "\n";
//	                        std::cout << "CURRENTVALS: " << (((rank + 1) * 8) + file - 1) << "\n";

//	                        std::cout << "1:" << !position[rank + 1][file - 1] == null << "\n";
//	                        std::cout << "2:" << (position[rank + 1][file - 1].getColor() == otherColor) << "\n";
//	                        std::cout << "3:" << (((rank + 1) * 8 + (file - 1)) == posToNum(validPassantSquare)) << "\n";

	                        if (checkIfValidSquare(rank+1, file-1) && position[rank+1][file-1] != null 
	                        		&& (position[rank + 1][file - 1].getColor() == otherColor 
	                                || ((rank + 1) * 8 + (file - 1)) == posToNum(validPassantSquare))) {

//	                            std::cout << "PLUSMINUS\n";
	                            String toPush = genMoveStr(PieceType.PAWN, (rank + 1), (file - 1), true);
//	                            std::cout << "sproigfiujhzxesrpyg: " << toPush << "\n";
	                            if (rank == 1) {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=Q");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=R");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=B");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=N");
	                            }
	                            else {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush);
	                            }
	                        }
	                        if (checkIfValidSquare(rank+1, file+1) && position[rank+1][file+1] != null 
	                        		&& (position[rank + 1][file + 1].getColor() == otherColor 
	                                || ((rank + 1) * 8 + (file + 1)) == posToNum(validPassantSquare))) {

//	                            std::cout << "PLUSPLUS\n";
	                            String toPush = genMoveStr(PieceType.PAWN, (rank + 1), (file + 1), true);
	                            if (rank == 1) {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=Q");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=R");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=B");
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush + "=N");
	                            }
	                            else {
	                                moveList.add(currPosStr + currPosStr.charAt(0) + toPush);
	                            }
	                        }
	                    }
	                }
	                if (currPiece.getType() == PieceType.ROOK) {
	                    int currRank = rank + 1;
	                    int currFile = file;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currRank;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank - 1;
	                    currFile = file;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currRank;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank;
	                    currFile = file + 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank;
	                    currFile = file - 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.ROOK, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                }
	                if (currPiece.getType() == PieceType.KNIGHT) {
	                    // Short circuit function inserted to cancel the if statement if the square is not valid.
	                    // up 2 right 1
	                    if (checkIfValidSquare(rank + 2, file + 1) && position[rank + 2][file + 1] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 2), (file + 1), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank + 2, file + 1) && position[rank + 2][file + 1].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 2), (file + 1), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // up 2 left 1
	                    if (checkIfValidSquare(rank + 2, file - 1) && position[rank + 2][file - 1] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 2), (file - 1), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank + 2, file - 1) && position[rank + 2][file - 1].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 2), (file - 1), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // up 1 right 2
	                    if (checkIfValidSquare(rank + 1, file + 2) && position[rank + 1][file + 2] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 1), (file + 2), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank + 1, file + 2) && position[rank + 1][file + 2].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 1), (file + 2), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // up 1 left 2
	                    if (checkIfValidSquare(rank + 1, file - 2) && position[rank + 1][file - 2] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 1), (file - 2), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank + 1, file - 2) && position[rank + 1][file - 2].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank + 1), (file - 2), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // down 2 right 1
	                    if (checkIfValidSquare(rank - 2, file + 1) && position[rank - 2][file + 1] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 2), (file + 1), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank - 2, file + 1) && position[rank - 2][file + 1].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 2), (file + 1), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // down 2 left 1
	                    if (checkIfValidSquare(rank - 2, file - 1) && position[rank - 2][file - 1] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 2), (file - 1), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank - 2, file - 1) && position[rank - 2][file - 1].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 2), (file - 1), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // down 1 right 2
	                    if (checkIfValidSquare(rank - 1, file + 2) && position[rank - 1][file + 2] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 1), (file + 2), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank - 1, file + 2) && position[rank - 1][file + 2].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 1), (file + 2), true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    // down 1 left 2
	                    if (checkIfValidSquare(rank - 1, file - 2) && position[rank - 1][file - 2] == null) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 1), (file - 2), false);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    else if (checkIfValidSquare(rank - 1, file - 2) && position[rank - 1][file - 2].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.KNIGHT, (rank - 1), (file - 2), true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                }
	                if (currPiece.getType() == PieceType.BISHOP) {
	                    int currRank = rank + 1;
	                    int currFile = file + 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currRank;
	                        ++currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    currRank = rank + 1;
	                    currFile = file - 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currRank;
	                        --currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    currRank = rank - 1;
	                    currFile = file + 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currRank;
	                        ++currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    currRank = rank - 1;
	                    currFile = file - 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currRank;
	                        --currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.BISHOP, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                }
	                if (currPiece.getType() == PieceType.QUEEN) {

	                    int currRank = rank + 1;
	                    int currFile = file;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currRank;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    currRank = rank - 1;
	                    currFile = file;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currRank;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                    currRank = rank;
	                    currFile = file + 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank;
	                    currFile = file - 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank + 1;
	                    currFile = file + 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currRank;
	                        ++currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank + 1;
	                    currFile = file - 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        ++currRank;
	                        --currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank - 1;
	                    currFile = file + 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currRank;
	                        ++currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }

	                    currRank = rank - 1;
	                    currFile = file - 1;
	                    while (checkIfValidSquare(currRank, currFile) && position[currRank][currFile] == null) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, false);
	                        moveList.add(currPosStr + toPush);
	                        --currRank;
	                        --currFile;
	                    }
	                    if (checkIfValidSquare(currRank, currFile) && position[currRank][currFile].getColor() == otherColor) {
	                        String toPush = genMoveStr(PieceType.QUEEN, currRank, currFile, true);
	                        moveList.add(currPosStr + toPush);
	                    }
	                }
	                if (currPiece.getType() == PieceType.KING) {
	                    for (int i = -1; i <= 1; i++) {
	                        for (int j = -1; j <= 1; j++) {
	                            if (!(i == 0 && j == 0) && checkIfValidSquare(rank + i, file + j) && position[rank + i][file + j] == null && !isInCheck(rank + i, file + j, color)
	                                && !checkIfKingAdjacent(rank + i, file + j, color)) {
	                                String toPush = genMoveStr(PieceType.KING, rank + i, file + j, false);
	                                moveList.add(currPosStr + toPush);
	                            }
	                            else if (!(i == 0 && j == 0) && checkIfValidSquare(rank + i, file + j) && position[rank + i][file + j].getColor() == otherColor) {
	                                String toPush = genMoveStr(PieceType.KING, rank + i, file + j, true);
	                                moveList.add(currPosStr + toPush);
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }

	    return moveList;
		
	}

	public int getKingPos(int color) {
		return 0;
	}

	public boolean isInCheck(int myRank, int myFile, int color) {
	    int rank = myRank;
	    int file = myFile;
	    int myColor = color;
		int otherColor = (myColor == Piece.BLACK ? Piece.WHITE : Piece.BLACK);
	    
	    //===============================================================================================================================================================
	    //===============================CHECKS IF KING IS IN CHECK BY OPPOSING PAWN=====================================================================================
	    //===============================================================================================================================================================
	    if (myColor == Piece.WHITE) {
	        if (checkIfValidSquare(rank + 1, file - 1) && position[rank + 1][file - 1].getColor() == otherColor && position[rank + 1][file - 1].getType() == PieceType.PAWN) {
	            return true;
	        }
	        if (checkIfValidSquare(rank + 1, file + 1) && position[rank + 1][file + 1].getColor() == otherColor && position[rank + 1][file + 1].getType() == PieceType.PAWN) {
	            return true;
	        }
	    }
	    else {
	        if (checkIfValidSquare(rank - 1, file - 1) && position[rank - 1][file - 1].getColor() == otherColor && position[rank - 1][file - 1].getType() == PieceType.PAWN) {
	            return true;
	        }
	        if (checkIfValidSquare(rank - 1, file + 1) && position[rank - 1][file + 1].getColor() == otherColor && position[rank - 1][file + 1].getType() == PieceType.PAWN) {
	            return true;
	        }
	    }
		

	    //===============================================================================================================================================================
	    //===============================CHECKS IF KING IS IN CHECK BY OPPOSING KNIGHT===================================================================================
	    //===============================================================================================================================================================

	    //000X0
	    //00000
	    //00N00
	    //00000
	    //00000
	    if (checkIfValidSquare(rank + 2, file + 1)) {
	        if (position[rank + 2][file + 1] == null) {

	        }
	        else if (position[rank + 2][file + 1].getColor() == otherColor && position[rank + 2][file + 1].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }
	    //0X000
	    //00000
	    //00N00
	    //00000
	    //00000
	    if (checkIfValidSquare(rank + 2, file - 1)) {
	        if (position[rank + 2][file - 1] == null) {

	        }
	        else if (position[rank + 2][file - 1].getColor() == otherColor && position[rank + 2][file - 1].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }
	    //00000
	    //0000X
	    //00N00
	    //00000
	    //00000
	    if (checkIfValidSquare(rank + 1, file + 2)) {
	        if (position[rank + 1][file + 2] == null) {

	        }
	        else if (position[rank + 1][file + 2].getColor() == otherColor && position[rank + 1][file + 2].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }
	    //00000
	    //X0000
	    //00N00
	    //00000
	    //00000
	    if (checkIfValidSquare(rank + 1, file - 2)) {
	        if (position[rank + 1][file - 2] == null) {

	        }
	        else if (position[rank + 1][file - 2].getColor() == otherColor && position[rank + 1][file - 2].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }
	    //00000
	    //00000
	    //00N00
	    //00000
	    //000X0
	    if (checkIfValidSquare(rank - 2, file + 1)) {
	        if (position[rank - 2][file + 1] == null) {

	        }
	        else if (position[rank - 2][file + 1].getColor() == otherColor && position[rank - 2][file + 1].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }

	    //00000
	    //00000
	    //00N00
	    //00000
	    //0X000
	    if (checkIfValidSquare(rank - 2, file - 1)) {
	        if (position[rank - 2][file - 1] == null) {

	        }
	        else if (position[rank - 2][file - 1].getColor() == otherColor && position[rank - 2][file - 1].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }

	    //00000
	    //00000
	    //00N00
	    //0000X
	    //00000
	    if (checkIfValidSquare(rank - 1, file + 2)) {
	        if (position[rank - 1][file + 2] == null) {

	        }
	        else if (position[rank - 1][file + 2].getColor() == otherColor && position[rank - 1][file + 2].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }

	    //00000
	    //00000
	    //00N00
	    //X0000
	    //00000
	    if (checkIfValidSquare(rank - 1, file - 2)) {
	        if (position[rank - 1][file - 2] == null) {

	        }
	        else if (position[rank - 1][file - 2].getColor() == otherColor && position[rank - 1][file - 2].getType() == PieceType.KNIGHT) {
	            return true;
	        }
	    }
	    

	    //===============================================================================================================================================================
	    //===============================CHECKS IF KING IS IN CHECK BY OPPOSING ROOK/QUEEN===============================================================================
	    //===============================================================================================================================================================

	    // Checks vertically above the king
	    int currRank = rank + 1;
	    int currFile = file;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) ++currRank;
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.ROOK
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or rook
	            return true;
	        }
	        else break;
	    }
	    // Checks vertically below the king
	    currRank = rank - 1;
	    currFile = file;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) --currRank;
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.ROOK
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or rook
	            return true;
	        }
	        else break;
	    }
	    // Checks to the right of the king
	    currRank = rank;
	    currFile = file + 1;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) ++currFile;
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.ROOK
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or rook
	            return true;
	        }
	        else break;
	    }
	    // Checks to the left of the king
	    currRank = rank;
	    currFile = file - 1;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) --currFile;
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.ROOK
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or rook
	            return true;
	        }
	        else break;
	    }

	    //===============================================================================================================================================================
	    //===============================CHECKS IF KING IS IN CHECK BY OPPOSING QUEEN/BISHOP=============================================================================
	    //===============================================================================================================================================================
	    // Checks NE direction from the king.
	    currRank = rank + 1;
	    currFile = file + 1;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) {
	            ++currRank;
	            ++currFile;
	        }
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.BISHOP
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or bishop
	            return true;
	        }
	        else break;
	    }
	    // Checks NW direction from the king.
	    currRank = rank + 1;
	    currFile = file - 1;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) {
	            ++currRank;
	            --currFile;
	        }
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.BISHOP
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or bishop
	            return true;
	        }
	        else break;
	    }
	    // Checks SE direction from the king.
	    currRank = rank - 1;
	    currFile = file + 1;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) {
	            --currRank;
	            ++currFile;
	        }
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.BISHOP
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or bishop
	            return true;
	        }
	        else break;
	    }
	    // Checks NE direction from the king.
	    currRank = rank - 1;
	    currFile = file - 1;
	    while (checkIfValidSquare(currRank, currFile)) {
	        if (position[currRank][currFile] == null) {
	            --currRank;
	            --currFile;
	        }
	        else if (position[currRank][currFile].getColor() == otherColor && (position[currRank][currFile].getType() == PieceType.BISHOP
	            || position[currRank][currFile].getType() == PieceType.QUEEN)) { // King in check by either queen or bishop
	            return true;
	        }
	        else break;
	    }
	    
		return false;
	    
	}

	public void print() {
	    for (int i = 8; i >= 0; i--) {
	        for (int j = 0; j < 9; j++) {
	            if (i == 8) {
	                //std::cout << (j + 2) << " ";
	            }
	            else if (j == 8) {
	                System.out.print(i + " ");
	            }
	            else {
	                if (!(position[i][j] == null)) {
                        System.out.print("[" + position[i][j].toString() + "] ");
	                }
	                else {
	                    System.out.print("[  ] ");
	                }
	            }
	        }
	        System.out.print('\n');
	    }
	}

	public void printMoves() {
		List<String> moves = getValidMoves((isWhitesTurn ? Piece.WHITE : Piece.BLACK));
		
		for (String move : moves) {
			System.out.println(move);
		}
	}

	public String getFen() {
		return this.myFen;
	}

	private boolean place(char currChar, int counter, PieceType type) {
		int rank = counter / 8;
		int file = counter % 8;
		Piece myPiece;
		try {
			myPiece = Piece.pieceBuilder(currChar);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		position[rank][file] = myPiece;
		
		System.out.println("TEST" + myPiece);
		return true;
	}

	private String genMoveStr(PieceType type, int rank, int file, boolean takes) {
		String toPush = "";
		int myRank = rank + 1;
		char fileLetter = (char) (file + 97);
		
		if (type == PieceType.PAWN) {
			if (takes) {
				toPush += 'x';
			}
			toPush += fileLetter + "" + myRank;
		} else {
			toPush += typeToChar(type);
			if (takes) {
				toPush += 'x';
			}
			toPush += fileLetter + "" + myRank;
		}
		
		return toPush;
	}

	private boolean checkIfValidSquare(int rank, int file) {
		return ((0 <= rank && rank < 8) && (0 <= file && file < 8));
	}

	private boolean checkIfKingAdjacent(int rank, int file, int color) {
		int myColor = color;
		int otherColor = (myColor == Piece.BLACK ? Piece.WHITE : Piece.BLACK);
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				Piece currPiece = position[rank + i][file + j];
				if (!(i == 0 && j == 0) && checkIfValidSquare(rank + i, file + j) && currPiece.getType() == PieceType.KING
						&& currPiece.getColor() == otherColor) {
					return true;
				}
			}
		}
		
		return false;
	}

	private int posToNum(String pos) {
		int file = ((int) pos.charAt(0) - 97);
		int rank = ((int) pos.charAt(1) - 48) - 1;

		return rank * 8 + file;
	}
	
	private char typeToChar(PieceType type) {
		switch(type) {
		case KING:
			return 'K';
		case QUEEN:
			return 'Q';
		case BISHOP:
			return 'B';
		case KNIGHT:
			return 'N';
		case ROOK:
			return 'R';
		default:
			return 'E';
		}
	}
	
	private char colorToChar(int color) {
		if (color == Piece.WHITE) {
			return 'w';
		}
		else if (color == Piece.BLACK) {
			return 'b';
		} else {
			throw new NumberFormatException("invalid color provided");
		}
		
	}
	
}
