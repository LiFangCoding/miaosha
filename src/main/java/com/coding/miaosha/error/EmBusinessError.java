package com.coding.miaosha.error;


public enum EmBusinessError implements CommonError {
  // Common error type. 10001
  PARAMETER_VALIDATION_ERROR(10001, "parameter not valid"), UNKNOWN_ERROR(10002, "unknown error"),

  // 10000 start is for the user info related error definition
  USER_NOT_EXIST(20001, "User not exist"), USER_LOGIN_FAIL(20002, "user telephone or password incorrect"),
  ;

  EmBusinessError(int errCode, String errMsg) {
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
