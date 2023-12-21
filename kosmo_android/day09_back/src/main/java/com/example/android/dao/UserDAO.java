package com.example.android.dao;

import java.util.HashMap;
import java.util.List;

import com.example.android.domain.QueryVO;
import com.example.android.domain.UserVO;

public interface UserDAO {
	public List<HashMap<String,Object>> list(QueryVO vo);
	public int total(QueryVO vo);
	public UserVO read(String uid);
	public void insert(UserVO vo);
	public void update(UserVO vo);
	
}
