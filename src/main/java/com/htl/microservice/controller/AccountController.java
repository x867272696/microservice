package com.htl.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.htl.microservice.controller.vo.Result;
import com.htl.microservice.service.BusinessService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private BusinessService businessService;
	
	@ApiOperation(value = "购买商品")
	@GetMapping("/buy")
	public Result<?> in(Integer userId, Integer productId, String flag){
		businessService.transaction(userId, productId, flag);
		return new Result<>();
	}
	
}
