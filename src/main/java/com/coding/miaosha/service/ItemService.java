package com.coding.miaosha.service;

import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.service.model.ItemModel;

import java.util.List;

public interface ItemService {
  ItemModel createItem(ItemModel itemModel) throws BusinessException;

  List<ItemModel> listItem();

  ItemModel getItemById(Integer id);
}
