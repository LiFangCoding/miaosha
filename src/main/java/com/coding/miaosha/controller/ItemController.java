package com.coding.miaosha.controller;

import com.coding.miaosha.controller.viewobject.ItemVO;
import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.response.CommonReturnType;
import com.coding.miaosha.service.ItemService;
import com.coding.miaosha.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController {

  @Autowired
  private ItemService itemService;

  @PostMapping(value = "/create", consumes = { CONTENT_TYPE_FORMED })
  @ResponseBody
  public CommonReturnType createItem(@RequestParam(name = "title") String title, @RequestParam(name = "description") String description, @RequestParam(name = "price") BigDecimal price,
      @RequestParam(name = "stock") Integer stock, @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
    ItemModel itemModel = new ItemModel();
    itemModel.setTitle(title);
    itemModel.setDescription(description);
    itemModel.setPrice(price);
    itemModel.setStock(stock);
    itemModel.setImgUrl(imgUrl);

    ItemModel itemModelForReturn = itemService.createItem(itemModel);
    ItemVO itemVO = convertVOFromModel(itemModelForReturn);

    return CommonReturnType.create(itemVO);
  }

  @GetMapping(value = "/get")
  @ResponseBody
  public CommonReturnType getItem(@RequestParam(name="id") Integer id) {
    ItemModel itemModel =  itemService.getItemById(id);

    ItemVO itemVO = convertVOFromModel(itemModel);

    return CommonReturnType.create(itemVO);
  }

  private ItemVO convertVOFromModel(ItemModel itemModel) {
    if (itemModel == null) {
      return null;
    }
    ItemVO itemVO = new ItemVO();
    BeanUtils.copyProperties(itemModel, itemVO);
    return itemVO;
  }
}
