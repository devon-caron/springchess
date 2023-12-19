package com.dpc.springchess.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpc.springchess.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByCredentials_Username(String username);
}
