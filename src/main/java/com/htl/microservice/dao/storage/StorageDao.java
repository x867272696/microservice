package com.htl.microservice.dao.storage;

import org.apache.ibatis.annotations.Param;

public interface StorageDao {

	void updateStorage(@Param("id") Integer id);

}
