package com.dpc.springchess.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Board {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User creator;
	
	@CreationTimestamp
	private Timestamp posted;
	
	private String fen;
	
	private String pgn;
}
