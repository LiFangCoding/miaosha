package com.coding.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserModel {
  private Integer id;
  @NotBlank(message = "Use name cannot be empty")
  private String name;

  @NotNull(message = "gender need filled")
  private String gender;

  @NotNull(message = "age need filled")
  @Min(value = 0, message = "age must be larger thant 0")
  @Max(value = 150, message = "age must be smaller thant 150")
  private Integer age;

  @NotBlank(message = "telephone cannot be empty")
  private String telephone;
  private String registerMode;
  private String thirdPartyId;

  @NotBlank(message = "password cannot be empty")
  private String encryptPassword;
}
