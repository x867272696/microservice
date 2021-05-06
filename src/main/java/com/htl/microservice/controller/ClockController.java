package com.htl.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.htl.microservice.controller.vo.Result;
import com.htl.microservice.service.ClockService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clock")
public class ClockController {
	
	@Autowired
	private ClockService clockService;
	
	@ApiOperation(value = "打卡")
	@GetMapping("/in")
	public Result<?> in(){
		
		return new Result<>(clockService.login() + "      " + clockService.clockIn());
	}
	
}
