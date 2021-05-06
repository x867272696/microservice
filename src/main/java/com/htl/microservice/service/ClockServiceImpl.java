package com.htl.microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ClockServiceImpl implements ClockService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String clockIn() {
		String url = "http://182.150.116.8:8089/api/hrm/kq/attendanceButton/punchButton";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Cookie", "ecology_JSessionid=aaaRJ5oIYX3DeaGaVaotx; loginidweaver=162; languageidweaver=7; JSESSIONID=aaaRJ5oIYX3DeaGaVaotx; loginuuids=162; __randcode__=f9d74b81-6a36-44f9-a3e6-5e27789e1725; EM_JSESSIONID=53F692D73974FBF5FF8BE6729C8A4D73");
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("date", "2020-10-16");
		params.add("signSection", "2020-10-16 07:30:00#2020-10-17 05:10:59");
		params.add("active", "1");
		params.add("belongtime", "17:30");		
		params.add("needSign", "1");
		params.add("type", "off");		
		params.add("workmins", "450");
		params.add("canSignTime", "05:10");			
		params.add("min_next", "-1");
		params.add("across", "0");				
		params.add("belongdate", "2020-10-16");
		params.add("datetime", "2020-10-16 17:30:00");
		params.add("min", "700");
		params.add("islastsign", "1");		
		params.add("serialid", "1");
		params.add("signAcross", "1");			
		params.add("signAcross_next", "0");
		params.add("signSectionTime", "2020-10-17 05:10:59");		
		params.add("signSectionBeginTime", "");
		params.add("isYellow", "1");			
		params.add("time", "17:30");
		params.add("isPunchOpen", "1");
		params.add("isacross", "0");
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		System.out.println(response.getBody());
		return response.getBody();
	}

	@Override
	public String login() {
		String url = "http://182.150.116.8:8089/api/hrm/login/checkLogin";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Cookie", "ecology_JSessionid=aaaRJ5oIYX3DeaGaVaotx; loginidweaver=162; languageidweaver=7; JSESSIONID=aaaRJ5oIYX3DeaGaVaotx; loginuuids=162; __randcode__=f9d74b81-6a36-44f9-a3e6-5e27789e1725; EM_JSESSIONID=53F692D73974FBF5FF8BE6729C8A4D73");
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("islanguid", "7");
		params.add("loginid", "T9yTUY3MfvFo0AZq5ODTpC/Z8JXq2icHnZQh+kY/9dhHmhdroXEVQjKK0rdQWcK4VNjW9Y0qy2eNPmFmHSMaLuOTAqt+RN02A1fdKRt5KOQ5MWJiEjlUtlvydqeDVyrQbFBgXEyDzx9d0Y7r4U1chkJeZgVoTKarIPhhwRJqeulhLpAB9s1dVB9nQc2SY04sZ6VUAQt/WTfm/YupZE2sAsk6HAuoAZ2yf2LnqKpnhiDxJVoHbUorzAy9mnc8r0cvi2DdeU6uPU6EedYx9ZN1ROy8xYQAX44NEzGNzXEoak60DbMJpRWQCf2WB/S75GFcLMy1dolFiKrTCN9pv2V4QQ==``RSA``");
		params.add("userpassword", "MBOOGgPETHM5OwgcWQSEj1TvIY3KaOXNUuxpCDz+zn/J1LBbvBsm2ml8T1xygi8ZWllcMKKzQH0+o+iMOZfNhwhwM18iY5IygnX5CSEC5596vwrNlg85Uhh3vZ2BwvsFB6SxTURk2xOgY2/pjdcFD2wwGrFrvoSBtnrx9QDB1bZI+jH67xOvMda3OLmmgW4xx0i26XUjpocmjV7Zq8GyJynCcwEMRZAWN+WrCptKZugm+xQzjwNVzA7M5t2NE62uAV0jEoNi3OlvqpYNDxyc4uCOY9WgE5KKAJU/hVpLOFk81Njaj5UUx7z+GxjzxYl5jviPvb9ztFQtmtYVhYnnOw==``RSA``");
		params.add("logintype", "1");		
		params.add("isie", "false");
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		System.out.println(response.getBody());
		return response.getBody();
	}
}
