package com.dpc.springchess.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class BoardRequestDto {
	private String fen;
	private String pgn;
}
