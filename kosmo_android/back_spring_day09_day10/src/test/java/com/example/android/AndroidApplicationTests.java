package com.example.android;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.android.dao.MysqlDAO;
import com.example.android.dao.UserDAO;
import com.example.android.domain.UserVO;

@SpringBootTest
class AndroidApplicationTests {
	
	@Autowired
	   MysqlDAO dao;
	   
	   @Autowired
	   UserDAO udao;
	   
	   @Test
	   void contextLoads() {
	      System.out.println("..........." + dao.now());
	   }
	   
//	   @Test
//	   void userList() {
//	      udao.list();
//	   }
	   
//	   @Test
//	   void userInsert() {
//	      UserVO vo = new UserVO();
//	      vo.setUid("white");
//	      vo.setUpass("pass");
//	      vo.setUname("김하얀");
//	      vo.setAddress1("서울시 강남구 압구정동");
//	      vo.setAddress2("현대아파트");
//	      vo.setPhone("010-9090-8080");
//	      udao.insert(vo);
//	   }
	   
	   @Test
	   void userRead() {
	      udao.read("white");
	   }
	   @Test
	   void userUpdate() {
		  UserVO vo = udao.read("white");
	      vo.setAddress1("서울시 영등포구 도림동");
	      udao.update(vo);
	   }

}
