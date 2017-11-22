package com.aia.service.mapper;

import java.util.List;

import com.aia.common.pojo.SeachItem;

public interface SeachItemMapper {

	public List<SeachItem> getSeachItemList();
	
	public SeachItem getSearchItemById( Long id);
}
