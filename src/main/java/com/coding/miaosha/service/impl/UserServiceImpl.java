package com.coding.miaosha.service.impl;

import com.coding.miaosha.dao.UserDOMapper;
import com.coding.miaosha.dao.UserPasswordDOMapper;
import com.coding.miaosha.dataobject.UserDO;
import com.coding.miaosha.dataobject.UserPasswordDO;
import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.error.EmBusinessError;
import com.coding.miaosha.service.UserService;
import com.coding.miaosha.service.model.UserModel;
import com.coding.miaosha.validator.ValidationResult;
import com.coding.miaosha.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDOMapper userDOMapper;

  @Autowired
  private UserPasswordDOMapper userPasswordDOMapper;

  @Autowired
  private ValidatorImpl validator;

  @Override
  public UserModel getUserById(Integer id) {
    UserDO userDO = userDOMapper.selectByPrimaryKey(id);
    if (userDO == null) {
      return null;
    }

    UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
    return convertFromDataObject(userDO, userPasswordDO);
  }

  @Override
  @Transactional
  public void register(UserModel userModel) throws BusinessException {
    if (userModel == null) {
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }
    ValidationResult result = validator.validate(userModel);
    if (result.isHasErrors()) {
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
    }

    UserDO userDO = convertFromModel(userModel);
    try {
      userDOMapper.insertSelective(userDO);
    } catch (DuplicateKeyException ex) {
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "telephone num is duplicated");
    }

    userModel.setId(userDO.getId());

    UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
    userPasswordDOMapper.insertSelective(userPasswordDO);

    return;
  }

  @Override
  public UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException {
    UserDO userDO = userDOMapper.selectByTelephone(telephone);

    if (userDO == null) {
      throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
    }

    UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
    UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

    if (!StringUtils.equals(encrptPassword, userModel.getEncryptPassword())) {
      throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
    }

    return userModel;
  }

  private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
    if (userModel == null) {
      return null;
    }

    UserPasswordDO userPasswordDO = new UserPasswordDO();
    userPasswordDO.setEncrptPassword(userModel.getEncryptPassword());
    userPasswordDO.setUserId(userModel.getId());
    return userPasswordDO;
  }

  private UserDO convertFromModel(UserModel userModel) {
    if (userModel == null) {
      return null;
    }

    UserDO userDO = new UserDO();
    // convert model to dataobject
    BeanUtils.copyProperties(userModel, userDO);

    return userDO;
  }

  private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
    if (userDO == null) {
      return null;
    }
    UserModel userModel = new UserModel();
    BeanUtils.copyProperties(userDO, userModel);

    if (userPasswordDO != null) {
      userModel.setEncryptPassword(userPasswordDO.getEncrptPassword());
    }

    return userModel;
  }
}
