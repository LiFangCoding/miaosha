package com.coding.miaosha.controller;

import com.coding.miaosha.controller.viewobject.UserVO;
import com.coding.miaosha.service.UserService;
import com.coding.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/get")
  @ResponseBody
  public UserVO getUser(@RequestParam(name = "id") Integer id) {
    UserModel userModel = userService.getUserById(id);
    return convertFromModel(userModel);
  }

  private UserVO convertFromModel(UserModel userModel) {
    if (userModel == null) {
      return null;
    }

    UserVO userVO = new UserVO();
    BeanUtils.copyProperties(userModel, userVO);
    return userVO;
  }
}
