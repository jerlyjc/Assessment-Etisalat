package com.assessment.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assessment.model.UserHdr;
import com.assessment.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class UserDetailService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(UserDetailService.class);	
	@Autowired
    private UserRepository userRepository;
	
	public UserDetailService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
            final UserHdr userhdr = userRepository.findByEmail(username);            
            if (userhdr == null) {            	
                throw new UsernameNotFoundException("No user found with email: " + username);
            }
            User user =  new User(userhdr.getEmail(),userhdr.getPassword(),Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            
            return user;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
	}

}
