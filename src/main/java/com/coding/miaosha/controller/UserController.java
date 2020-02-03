package com.coding.miaosha.controller;

import com.coding.miaosha.controller.viewobject.UserVO;
import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.error.EmBusinessError;
import com.coding.miaosha.response.CommonReturnType;
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
  public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
    UserModel userModel = userService.getUserById(id);

    if (userModel == null) {
      throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
    }
    // the domain user model convert to viewobject for UI
    UserVO userVO = convertFromModel(userModel);
    // return common type
    return CommonReturnType.create(userVO);
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
