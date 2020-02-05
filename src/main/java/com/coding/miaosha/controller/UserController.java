package com.coding.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.coding.miaosha.controller.viewobject.UserVO;
import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.error.EmBusinessError;
import com.coding.miaosha.response.CommonReturnType;
import com.coding.miaosha.service.UserService;
import com.coding.miaosha.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

  @Autowired
  private UserService userService;

  @Autowired
  private HttpServletRequest httpServletRequest;

  /**
   * User register interface
   */
  @PostMapping(value="/register", consumes={CONTENT_TYPE_FORMED})
  @ResponseBody
  public CommonReturnType register(@RequestParam(name="telephone") String telephone,
      @RequestParam(name="otpCode") String otpCode,
      @RequestParam(name="name") String name,
      @RequestParam(name="gender") String gender,
      @RequestParam(name="age") Integer age,
      @RequestParam(name="password") String password) throws BusinessException {
    // Validate telephone and otpcode is same
    String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
    if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "otpcode is not same");
    }

    // User register process
    UserModel userModel = new UserModel();
    userModel.setName(name);
    userModel.setGender(gender);
    userModel.setAge(age);
    userModel.setTelephone(telephone);
    userModel.setRegisterMode("byphone");
    userModel.setEncryptPassword(MD5Encoder.encode(password.getBytes()));
    userService.register(userModel);
    return CommonReturnType.create(null);
  }

  /**
   * User get opt message interface
   */
  @PostMapping(value="/getotp", consumes={CONTENT_TYPE_FORMED})
  @ResponseBody
  public CommonReturnType getOtp(@RequestParam(name="telephone") String telephone) {
    /**
     * According to rule generate OTP code
     */
    Random random = new Random();
    int randomInt = random.nextInt(99999);
    randomInt += 10000;
    String otpCode = String.valueOf(randomInt);

    /**
     * Connect OTP code with user telephone num. Use httpsession to combine telephone and code
     */
    httpServletRequest.getSession().setAttribute(telephone, otpCode);

    /**
     * Send OTP code to user by message tunnel
     */
    System.out.println("telephone = " + telephone + " & optCode = " + otpCode);

    return CommonReturnType.create(null);
  }

  @GetMapping("/get")
  @ResponseBody
  public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
    UserModel userModel = userService.getUserById(id);

    if (userModel == null) {
      userModel.setEncryptPassword("123");
//      throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
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
