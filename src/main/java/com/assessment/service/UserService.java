package com.assessment.service;

import com.assessment.model.UserDTO;
import com.assessment.model.UserHdr;

public interface UserService {
	UserHdr registerUser(UserDTO userDto);
}
