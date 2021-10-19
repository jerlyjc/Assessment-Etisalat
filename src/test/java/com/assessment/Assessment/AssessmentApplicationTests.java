package com.assessment.Assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AssessmentApplicationTests {

	@Value("${weather.api}")
	private String weatherApi;
	
	@Value("${weather.api-key}")
	private String weatherApiKey;
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
    @Test
    public void testgetWeatherByCity() throws Exception 
    {
    	String cityParam=new StringBuilder().append("London").append(",").append("uk").toString();
		
		StringBuilder url=new StringBuilder().append(weatherApi)
				.append("?appid=").append(weatherApiKey).append("&q=").append(cityParam);
		 
		ResponseEntity<String> result= restTemplate.getForEntity(url.toString(), String.class);
		assertEquals(200, result.getStatusCodeValue());	    
    }
    
    @Test
    public void testgetWeatherByCoords() throws Exception 
    {
    	StringBuilder url=new StringBuilder().append(weatherApi)
				.append("?appid=").append(weatherApiKey).append("&lat=55.5")
				.append("&lon=37.5");	    
		 
		ResponseEntity<String> result= restTemplate.getForEntity(url.toString(), String.class);
		assertEquals(200, result.getStatusCodeValue());	    
    }

}
