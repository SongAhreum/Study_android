package com.example.android.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.android.domain.QueryVO;
import com.example.android.domain.ShopVO;

@Repository
public class ShopDAOImpl implements ShopDAO{

	@Autowired
	SqlSession session;
	String namespace="com.example.mapper.ShopMapper";
	
	@Override
	public List<HashMap<String, Object>> list(QueryVO vo) {
		vo.setStart((vo.getPage()-1)*10);
		return session.selectList(namespace+".list",vo);
	}
	@Override
	public int total() {
		return session.selectOne(namespace+".total");	
	}
	@Override
	public void insert(ShopVO vo) {
		session.insert(namespace+".insert",vo);
		
	}
	@Override
	public ShopVO read(int pid) {
		return session.selectOne(namespace+".read",pid);
	}
	@Override
	public void update(ShopVO vo) {
		session.update(namespace+".update",vo);	
	}
	@Override
	public void updateImage(ShopVO vo) {
		session.update(namespace+".updateImage",vo);		
	}
}
