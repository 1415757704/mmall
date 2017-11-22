package com.aia.portal.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;

public interface ContentCatService {

	List getContentCatList(Long parentId);

	E3Result addContentCategory(Long parentId, String name); 
	
	E3Result updateContentCategory(Long id, String name);
	
	E3Result deleteContentCategory(Long id);
	
	List getContentCatList(Long categoryId,Integer page,Integer rows);

}
