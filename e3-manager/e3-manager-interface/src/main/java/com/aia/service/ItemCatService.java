package com.aia.service;

import java.util.List;

import com.aia.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatList (long parentId);
}
