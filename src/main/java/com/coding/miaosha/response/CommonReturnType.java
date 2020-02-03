package com.coding.miaosha.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonReturnType {
  /**
   * The return result for corresponding request.
   * "success" or "fail"
   */
  private String status;
  /**
   * if status=success, inside data, return the needed json to front end
   * if status=fail, inside data, use the common error code format
   */
  private Object data;

  public static CommonReturnType create(Object result) {
    return CommonReturnType.create(result, "success");
  }

  public static CommonReturnType create(Object result, String status) {
    CommonReturnType type = new CommonReturnType();
    type.setStatus(status);
    type.setData(result);
    return type;
  }
}
