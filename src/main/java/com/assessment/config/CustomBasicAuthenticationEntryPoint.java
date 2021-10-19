package com.assessment.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.assessment.ErrorCodes;
import com.assessment.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint  {
	
	@Override
    public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) 
      throws IOException{
		final ErrorResponse error = new ErrorResponse(ErrorCodes.USER_NOT_FOUND,ex.getMessage(),
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));   
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        StringWriter sw=new StringWriter();
        new ObjectMapper().writeValue(sw, error);
        writer.println(sw.toString());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("REALM");
        super.afterPropertiesSet();
    }
	
}
