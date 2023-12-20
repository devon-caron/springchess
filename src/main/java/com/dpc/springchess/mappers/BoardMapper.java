package com.dpc.springchess.mappers;

import java.util.List;

import com.dpc.springchess.dtos.BoardResponseDto;
import com.dpc.springchess.entities.Board;

public interface BoardMapper {
	List<BoardResponseDto> entitiesToDtos(List<Board> boards);
}
