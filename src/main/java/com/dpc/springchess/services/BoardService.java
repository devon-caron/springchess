package com.dpc.springchess.services;

import java.util.List;

import com.dpc.springchess.dtos.BoardRequestDto;

public interface BoardService {

	List<String> getMoves(BoardRequestDto boardRequestDto);

	List<String> getFen();

}
