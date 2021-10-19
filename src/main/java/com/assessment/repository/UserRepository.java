package com.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.assessment.model.UserHdr;

public interface UserRepository extends JpaRepository<UserHdr, Long> {
	UserHdr findByName(String name);
	
	UserHdr findByEmail(String email);
}
