package com.aia.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aia.common.pojo.EasyUITreeNode;
import com.aia.mapper.TbItemCatMapper;
import com.aia.pojo.TbItemCat;
import com.aia.pojo.TbItemCatExample;
import com.aia.pojo.TbItemCatExample.Criteria;
import com.aia.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		
		ArrayList<EasyUITreeNode> treeNodes = new ArrayList<>();
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = itemCatMapper.selectByExample(example);
		for (TbItemCat itemCat : selectByExample) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent() ? "closed" : "open");
			treeNodes.add(node);
		}
		return treeNodes;
	}

}
