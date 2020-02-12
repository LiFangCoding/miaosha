package com.coding.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemModel {
  private Integer id;

  @NotBlank(message="name cannot be empty")
  private String title;

  @NotNull(message = "price cannot be empty")
  @Min(value=0, message = "price need to be larger than 0")
  private BigDecimal price;

  @NotNull(message="stock need to be filled")
  private Integer stock;

  @NotBlank(message="description cannot be empty")
  private String description;

  private Integer sales;

  @NotBlank(message="image description cannot be empty")
  private String imageUrl;
}
