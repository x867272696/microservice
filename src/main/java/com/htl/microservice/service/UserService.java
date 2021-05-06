package com.htl.microservice.service;

import com.htl.microservice.entity.User;

public interface UserService {

	User getRedisUser(Integer id);

	String changeMoney(String id);

	Object buy();

	Object errorBuy();

	Object lockBuy();

}
