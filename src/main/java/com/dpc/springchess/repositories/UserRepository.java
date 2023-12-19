package com.dpc.springchess.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dpc.springchess.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	@Query(value="SELECT u FROM User u WHERE u.credentials.username = :username")
	Optional<User> findByCredentialsUsername(@Param("username") String username);
}
