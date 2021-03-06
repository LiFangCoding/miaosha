package com.coding.miaosha.error;

/**
 * 包装器业务异常实现
 */
public class BusinessException extends Exception implements CommonError{

  private CommonError commonError;

  // Accept the EmBusinessError parameter pass for abnormal business error
  public BusinessException(CommonError commonError) {
    super();
    this.commonError = commonError;
  }

  // Accept Customized errMsg for abnormal business error
  public BusinessException(CommonError commonError, String errMsg) {
    super();
    this.commonError = commonError;
    this.commonError.setErrMsg(errMsg);
  }

  @Override
  public int getErrCode() {
    return this.commonError.getErrCode();
  }

  @Override
  public String getErrMsg() {
    return this.commonError.getErrMsg();
  }

  @Override
  public CommonError setErrMsg(String errMsg) {
    this.commonError.setErrMsg(errMsg);
    return this;
  }
}
