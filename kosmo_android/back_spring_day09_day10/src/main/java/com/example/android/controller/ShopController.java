package com.example.android.controller;

import java.io.File;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.android.dao.ShopDAO;
import com.example.android.domain.QueryVO;
import com.example.android.domain.ShopVO;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	ShopDAO dao;
	
	
	@GetMapping("/list")
	public HashMap<String,Object> list(QueryVO vo){
		HashMap<String,Object> map = new  HashMap<>();
		map.put("list", dao.list(vo)); 
		map.put("total", dao.total()); 		
		return map;
	}
	
	@PostMapping("/insert")
	public void insert(@RequestBody ShopVO vo) {
		dao.insert(vo);
	}
	
	@GetMapping("/read/{pid}")
	public ShopVO read(@PathVariable("pid") int pid) {
		return dao.read(pid);
	}
	@PostMapping("/update")
	public void update(@RequestBody ShopVO vo) {
		
		dao.update(vo);
	}
	@PostMapping("/upload/image")
	public void update(@RequestParam("pid") int pid,MultipartHttpServletRequest multipart) {
		try {
			MultipartFile file = multipart.getFile("file");
			String filePath = "/upload/shop/";
			String fileName = pid+"_"+System.currentTimeMillis()+".jpg" ;
			file.transferTo(new File("d:"+filePath+fileName));
			
			ShopVO vo = new ShopVO();
			vo.setPid(pid);
			vo.setImage(filePath+fileName);
			dao.updateImage(vo);
		}catch(Exception e) {
			System.out.println("이미지 업로드 오류 :"+e.toString());
		}
		
	}
	
	
}
