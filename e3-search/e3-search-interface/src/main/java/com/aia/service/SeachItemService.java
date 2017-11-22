package com.aia.service;

import com.aia.common.pojo.SeachResult;

import cn.e3mall.common.utils.E3Result;

public interface SeachItemService {
	
	public E3Result importAllSeachIntoSolr() throws Exception;

	public SeachResult seachFromSolrBykeyword(String keyword, int page, int rows) throws Exception;
	
	//根据id添加一个商品记录到索引库中
	public void addDocument(Long id);
}
