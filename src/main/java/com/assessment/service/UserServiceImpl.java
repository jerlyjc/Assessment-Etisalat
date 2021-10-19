package com.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assessment.exception.UserAlreadyExistException;
import com.assessment.model.UserDTO;
import com.assessment.model.UserHdr;
import com.assessment.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepostiory;
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserHdr registerUser(UserDTO userDto) {
		// TODO Auto-generated method stub
		if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
        }
		UserHdr user= new UserHdr();
		user.setName(userDto.getName());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());		
		userRepostiory.save(user);
		return user;
	}
	private boolean emailExists(final String email) {
        return userRepostiory.findByEmail(email) != null;
    }

}
