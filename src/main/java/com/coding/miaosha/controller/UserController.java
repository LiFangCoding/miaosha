package com.coding.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.coding.miaosha.controller.viewobject.UserVO;
import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.error.EmBusinessError;
import com.coding.miaosha.response.CommonReturnType;
import com.coding.miaosha.service.UserService;
import com.coding.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @PostMapping(value = "/register", consumes = { CONTENT_TYPE_FORMED })
  @ResponseBody
  public CommonReturnType register(@RequestParam(name = "telephone") String telephone, @RequestParam(name = "otpCode") String otpCode, @RequestParam(name = "name") String name,
      @RequestParam(name = "gender") String gender, @RequestParam(name = "age") Integer age, @RequestParam(name = "password") String password)
      throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
    String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
    if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "otpcode is not same");
    }

    UserModel userModel = new UserModel();
    userModel.setName(name);
    userModel.setGender(gender);
    userModel.setAge(age);
    userModel.setTelephone(telephone);
    userModel.setRegisterMode("byphone");
    userModel.setEncryptPassword(this.EncodeByMd5(password));
    userService.register(userModel);
    return CommonReturnType.create(null);
  }

  public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    BASE64Encoder base64en = new BASE64Encoder();

    String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
    return newstr;
  }

  @PostMapping(value = "/getotp", consumes = { CONTENT_TYPE_FORMED })
  @ResponseBody
  public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
    Random random = new Random();
    int randomInt = random.nextInt(99999);
    randomInt += 10000;
    String otpCode = String.valueOf(randomInt);

    httpServletRequest.getSession().setAttribute(telephone, otpCode);

    System.out.println("telephone = " + telephone + " & optCode = " + otpCode);

    return CommonReturnType.create(null);
  }

  @GetMapping("/get")
  @ResponseBody
  public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
    UserModel userModel = userService.getUserById(id);

    if (userModel == null) {
      throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
    }
    UserVO userVO = convertFromModel(userModel);
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
