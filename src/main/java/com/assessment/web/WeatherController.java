package com.assessment.web;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.assessment.model.UserHdr;
import com.assessment.model.UserDTO;
import com.assessment.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping("/weatherInfo")
public class WeatherController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${weather.api}")
	private String weatherApi;
	
	@Value("${weather.api-key}")
	private String weatherApiKey;
	
	@Autowired
	private UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(WeatherController.class);	
	
	@RequestMapping(value = "/byCity", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getWeather(@RequestParam("city") String city,@RequestParam("countrycode") String countrycode) {
		logger.info("Inside weather controller");
		
		String cityParam=new StringBuilder().append(city).append(",").append(countrycode).toString();
		
		StringBuilder url=new StringBuilder().append(weatherApi)
				.append("?appid=").append(weatherApiKey).append("&q=").append(cityParam);	  
		        
		String weather = restTemplate.getForObject(url.toString(), String.class);
		return weather;
		
	}
	@RequestMapping(value = "/byCoords", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getWeatherByCoordinates(@RequestParam("latitude") String lat,@RequestParam("longitude") String longitude) {
		logger.info("Inside weather controller");	
			
		StringBuilder url=new StringBuilder().append(weatherApi)
				.append("?appid=").append(weatherApiKey).append("&lat=").append(lat)
				.append("&lon=").append(longitude);	          
		String weather = restTemplate.getForObject(url.toString(), String.class);
		return weather;
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody UserHdr register(@Valid @RequestBody UserDTO userDto) {
		logger.info("Inside weather controller: "+userDto.getName());
		logger.info("Inside weather controller: "+userDto.getEmail());	
		logger.info("Inside weather controller: "+userDto.getPassword());	
		
		return userService.registerUser(userDto);
		
	}
}
