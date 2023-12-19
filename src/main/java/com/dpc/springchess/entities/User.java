package com.dpc.springchess.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="user_table")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="creator")
	private List<Board> games;
	
	@ManyToMany(mappedBy="followers", fetch=FetchType.EAGER)
	private List<User> following;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="followers_following")
	private List<User> followers;
	
	@Embedded
	private Credentials credentials;
	
}
