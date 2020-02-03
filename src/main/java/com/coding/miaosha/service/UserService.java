package com.coding.miaosha.service;

import com.coding.miaosha.service.model.UserModel;

public interface UserService {
  // Get user object from user id
  UserModel getUserById(Integer id);
}
