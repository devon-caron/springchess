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
public class Credentials {
	
	@Column(unique=true, nullable=false)
	private String username;
	
	private String password;
}
