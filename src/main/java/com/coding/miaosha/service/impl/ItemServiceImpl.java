package com.coding.miaosha.service.impl;

import com.coding.miaosha.dao.ItemDOMapper;
import com.coding.miaosha.dao.ItemStockDOMapper;
import com.coding.miaosha.dataobject.ItemDO;
import com.coding.miaosha.dataobject.ItemStockDO;
import com.coding.miaosha.error.BusinessException;
import com.coding.miaosha.error.EmBusinessError;
import com.coding.miaosha.service.ItemService;
import com.coding.miaosha.service.model.ItemModel;
import com.coding.miaosha.validator.ValidationResult;
import com.coding.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
  @Autowired
  private ValidatorImpl validator;

  @Autowired
  private ItemDOMapper itemDOMapper;

  @Autowired
  private ItemStockDOMapper itemStockDOMapper;

  private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
    if (itemModel == null) {
      return null;
    }

    ItemDO itemDO = new ItemDO();
    BeanUtils.copyProperties(itemModel, itemDO);
    itemDO.setPrice(itemModel.getPrice().doubleValue());
    return itemDO;
  }

  private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
    if (itemModel == null) {
      return null;
    }

    ItemStockDO itemStockDO = new ItemStockDO();
    itemStockDO.setItemId(itemModel.getId());
    itemStockDO.setStock(itemModel.getStock());
    return itemStockDO;
  }

  @Override
  @Transactional
  public ItemModel createItem(ItemModel itemModel) throws BusinessException {
    ValidationResult result = validator.validate(itemModel);
    if (result.isHasErrors()) {
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
    }

    ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);

    itemDOMapper.insertSelective(itemDO);
    itemModel.setId(itemDO.getId());

    ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
    itemStockDOMapper.insertSelective(itemStockDO);
    return this.getItemById(itemModel.getId());
  }

  @Override
  public List<ItemModel> listItem() {
    return null;
  }

  @Override
  public ItemModel getItemById(Integer id) {
    ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);

    if (itemDO == null) {
      return null;
    }

    ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
    ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);

    return itemModel;
  }

  private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {
    ItemModel itemModel = new ItemModel();
    BeanUtils.copyProperties(itemDO, itemModel);
    itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
    itemModel.setStock(itemStockDO.getStock());

    return itemModel;
  }
}
