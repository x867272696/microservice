package com.htl.microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.htl.microservice.dao.RedisUserDao;
import com.htl.microservice.entity.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private RedisUserDao redisUserDao;
	
	@Override
	public String changeMoney(String id){
		return redisUserDao.changeMoney(id);
	}
	
	@Override
	public User getRedisUser(Integer id){
		User user = new User();
		String name = redisUserDao.getUser(id.toString());
		user.setName(name);
		return user;
	}

	
	@Override
	public Object buy() {
		return redisUserDao.buy();
	}
	
	@Override
	public Object errorBuy() {
		return redisUserDao.errorBuy();
	}
	
	@Override
	public Object lockBuy() {
		return redisUserDao.lockBuy();
	}
}
