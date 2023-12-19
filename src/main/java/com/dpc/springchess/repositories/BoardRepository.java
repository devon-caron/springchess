package com.dpc.springchess.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpc.springchess.entities.Board;

public interface BoardRepository extends JpaRepository<Board,Long>{

}
