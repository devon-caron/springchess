package com.dpc.springchess.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.dpc.springchess.dtos.BoardResponseDto;
import com.dpc.springchess.entities.Board;

@Mapper(componentModel="spring")
public interface BoardMapper {
	List<BoardResponseDto> entitiesToDtos(List<Board> boards);
	BoardResponseDto entityToDto(Board board);
}
