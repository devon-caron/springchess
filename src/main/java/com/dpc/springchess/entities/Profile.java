package com.dpc.springchess.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Profile {
	
	@Column(unique=true, nullable=false)
	private String name;
	
	private String title;
	
	private String rating;
}
