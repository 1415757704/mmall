package com.aia.service;

import com.aia.common.pojo.EasyUIDataGridResult;
import com.aia.pojo.TbItem;
import com.aia.pojo.TbItemDesc;

import cn.e3mall.common.utils.E3Result;

public interface ItemService {

	TbItem getItemById(Long itemId);
	
	EasyUIDataGridResult getItemList(Integer page,Integer rows);
	
    E3Result saveItem(TbItem tbItem, String desc);
    
    TbItemDesc getItemDescById(Long itemId);
}
