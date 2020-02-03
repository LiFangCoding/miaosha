package com.coding.miaosha.error;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public enum EmBusinessError implements CommonError{
  // Common error type. 00001
  PARAMETER_VALIDATION_ERROR(00001, "parameter not valid"),

  // 10000 start is for the user info related error definition
  USER_NOT_EXIST(10001, "User not exist")
  ;

  private EmBusinessError(int errCode, String errMsg) {
    this.errCode = errCode;
    this.errMsg = errMsg;
  }
  private int errCode;
  private String errMsg;


  @Override
  public int getErrCode() {
    return this.errCode;
  }

  @Override
  public String getErrMsg() {
    return this.errMsg;
  }

  @Override
  public CommonError setErrMsg(String errMsg) {
    this.errMsg = errMsg;
    return this;
  }
}
