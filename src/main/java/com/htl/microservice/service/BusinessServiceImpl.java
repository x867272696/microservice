package com.htl.microservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htl.microservice.dao.account.AccountDao;
import com.htl.microservice.dao.storage.StorageDao;

@Service
public class BusinessServiceImpl implements BusinessService{
	 
	private static final Logger log = LoggerFactory.getLogger(BusinessService.class);
 
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private StorageDao storageDao;
 
	@Transactional
	public String transaction(Integer userId, Integer productId, String flag) {
		bisiness(userId, productId, flag);
		return "操作成功";
	}
 
	private void bisiness(Integer userId, Integer productId, String flag) {
		log.info("==业务开始==");
		accountDao.updateAccount(userId);
		if ("1".equals(flag)) {
			int i = 1 / 0;
		}
		storageDao.updateStorage(productId);
		log.info("==业务结束==");
	}
	
}
