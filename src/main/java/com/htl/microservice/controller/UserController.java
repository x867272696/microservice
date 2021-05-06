package com.htl.microservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.htl.microservice.controller.vo.Result;
import com.htl.microservice.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
	
	public volatile static List<String> list = new ArrayList<>();
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "新增用户信息")
	@GetMapping("/add_user")
	public void add_user(HttpServletResponse response){

		        response.setHeader("Content-Type", "text/event-stream;charset=UTF-8");
		        response.setHeader("Cache-Control", "no-cache");
		        try {
		        	while(true) {
			            response.getWriter().write("id: 1\n");
			            response.getWriter().write("event: text\n");
			            response.getWriter().write("type: text\n");
			            response.getWriter().write("data: " + "hello" + "\n\n");
			            response.getWriter().flush();
			            
			            Thread.sleep(2000);
		        	}
		        } catch (Exception e) {
		            try {
		                response.getWriter().write("id:1\n");
		                response.getWriter().write("event:text\n");
		                response.getWriter().write("data:nodata\n\n");
		                response.getWriter().flush();
		            } catch (IOException ioException) {
		                ioException.printStackTrace();
		            }
		 
		        }
	}
	
	@ApiOperation(value = "查询缓存中用户信息")
	@GetMapping("/redis_user_info")
	public Result<?> redis_user_info(Integer id){
		return new Result<>(userService.getRedisUser(id));
	}
	
	@ApiOperation(value = "修改余额")
	@PostMapping("/change_money")
	public Result<?> change_money(@RequestBody Map<String, String> map, HttpServletResponse response){
		System.out.println(map);
		response.setHeader("auth", "login");
		return new Result<>();
	}
	
	@ApiOperation(value = "get_date")
	@GetMapping("/get_date")
	public Result<?> get_date(){
		return new Result<>(new Date().getTime()/1000);
	}
	
	@ApiOperation(value = "buy")
	@GetMapping("/buy")
	public Result<?> buy(){
		return new Result<>(userService.buy());
	}
	
	@ApiOperation(value = "errorBuy")
	@GetMapping("/errorBuy")
	public Result<?> errorBuy(){
		return new Result<>(userService.errorBuy());
	}
	
	@ApiOperation(value = "lockBuy")
	@GetMapping("/lockBuy")
	public Result<?> lockBuy(){
		return new Result<>(userService.lockBuy());
	}
	
	@ApiOperation(value = "测试内存溢出")
	@GetMapping("/add_string")
	public Result<?> add_string(){
		long i = 1;
		while(i > 0) {
			System.out.println(i++);
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
			list.add(new String("这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的.这个字符串是用来测试内存溢出的."));
		}
		return new Result<>();
	}
	
	public static void main(String[] args) {
		long i = 0;
		while(true) {
			System.out.println(i++);
		}
	}
}
