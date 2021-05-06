package com.htl.microservice.dao.account;

import org.apache.ibatis.annotations.Param;

public interface AccountDao {

	void updateAccount(@Param("id") Integer id);

}
