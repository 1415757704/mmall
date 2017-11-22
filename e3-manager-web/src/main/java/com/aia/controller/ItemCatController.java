package com.aia.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aia.common.pojo.EasyUITreeNode;
import com.aia.service.ItemCatService;

@Controller
public class ItemCatController {

	Logger logger  = Logger.getLogger(ItemCatController.class);
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/item/cat/list")
	@ResponseBody 
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id",defaultValue="0")long parentId) {
		List<EasyUITreeNode> itemCatList = null;
		try {
			itemCatList = itemCatService.getItemCatList(parentId);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return itemCatList;
	}
}
