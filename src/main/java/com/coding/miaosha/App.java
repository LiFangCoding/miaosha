package com.coding.miaosha;

import com.coding.miaosha.dao.UserDOMapper;
import com.coding.miaosha.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"com.coding.miaosha"})
@RestController
@MapperScan("com.coding.miaosha.dao")
public class App {
  @Autowired
  private UserDOMapper userDOMapper;

//  @RequestMapping("/")
//  public String home() {
//    UserDO userDO = userDOMapper.selectByPrimaryKey(1);
//    if (userDO == null) {
//      return "user not exist";
//    } else {
//      return userDO.getName();
//    }
//  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
    SpringApplication.run(App.class, args);
  }
}
