package com.dpc.springchess.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dpc.springchess.dtos.BoardRequestDto;
import com.dpc.springchess.instances.BoardInstance;
import com.dpc.springchess.services.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardInstance myBoard = new BoardInstance()

	@Override
	public List<String> getMoves(BoardRequestDto boardRequestDto) {
		BoardInstance myBoard = new BoardInstance(boardRequestDto.getFen());
		
		return myBoard.getValidMoves();
	}

	@Override
	public List<String> getFen() {
		BoardInstance myBoard = new BoardInstance(boardRequestDto.getFen())
		
		return null;
	}

}
