package com.example.android.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.android.dao.UserDAO;
import com.example.android.domain.QueryVO;
import com.example.android.domain.UserVO;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserDAO dao;
	//로그인
	@PostMapping("/login")
	public int login(@RequestBody UserVO vo) {
		int result = 0; 
		UserVO user = dao.read(vo.getUid());
		//uid로 read()한 값이 null -> result = 0
		if(user.getUid() != null) { 
			if(!user.getUpass().equals(vo.getUpass())) {
			result = 2; //비밀번호 불일치
			} else {
				result = 1; // 비밀번호일치 -> login
			}
		}
		
		return result;
	}
	
	//사용자정보수정
	@PostMapping("update")
	public void update(@RequestBody UserVO vo) {
		dao.update(vo);
	}
	
	//사용자읽기
	@GetMapping("/read/{uid}")
	public UserVO read(@PathVariable("uid") String uid) {
		return dao.read(uid);
	}
	
	//사용자등록
	@PostMapping("/insert")
	public void insert(@RequestBody UserVO vo) {
		dao.insert(vo);
	}
	
	//사용자리스트
	@GetMapping("/list.json")
	public HashMap<String,Object> list(QueryVO vo){
	
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("list",dao.list(vo));
		map.put("total",dao.total(vo));
		return map;
	}	
	
}
