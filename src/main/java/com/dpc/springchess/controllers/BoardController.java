package com.dpc.springchess.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpc.springchess.dtos.BoardRequestDto;
import com.dpc.springchess.services.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/moves") 
	List<String> getMoves(@RequestBody BoardRequestDto boardRequestDto) {
		return boardService.getMoves(boardRequestDto);
	}
	
	@GetMapping("/fen")
	List<String> getFen() {
		return boardService.getFen();
	}
}