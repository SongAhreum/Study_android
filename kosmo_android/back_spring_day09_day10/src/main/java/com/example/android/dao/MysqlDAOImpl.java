package com.example.android.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MysqlDAOImpl implements MysqlDAO{

	@Autowired
	SqlSession session;
	String namespace="com.example.mapper.MysqlMapper";
	
	@Override
	public String now() {
		return session.selectOne(namespace+".now");
	}
}
